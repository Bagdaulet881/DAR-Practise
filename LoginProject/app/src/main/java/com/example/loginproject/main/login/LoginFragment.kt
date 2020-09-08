package com.example.loginproject.main.login

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.loginproject.MainActivity.Companion.db
import com.example.loginproject.R
import com.example.loginproject.data.interfaces.LoginView
import com.example.loginproject.data.network.ClientInfo
import com.example.loginproject.data.presenter.LoginPresenter
import kotlinx.android.synthetic.main.fragment_login.*
import androidx.navigation.fragment.findNavController
import com.example.loginproject.data.interfaces.ProfileView
import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.UserInfo
import com.example.loginproject.data.presenter.ProfilePresenter
import com.example.loginproject.main.register.RegisterFragmentDirections
import kotlinx.android.synthetic.main.fragment_login.btnSignin
import kotlinx.android.synthetic.main.fragment_login.tvError
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_register.*


class LoginFragment : Fragment(), LoginView, ProfileView {

    val presenter = LoginPresenter(this)
    val presenterProfile = ProfilePresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.progressBar.visibility = View.VISIBLE
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        presenter.getClientInfo()
        //touch listener for showPassword

        etPassword.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= etPassword.getRight() - etPassword.getCompoundDrawables().get(
                        DRAWABLE_RIGHT
                    ).getBounds().width()
                ) {
                    showPassword()
                    return@OnTouchListener true
                }
            }
            false
        })
        btnSignin.setOnClickListener{
            val type = view.etUsername.text.toString()
            if(view.etUsername.text.isNotEmpty() && view.etPassword.text.isNotEmpty()){
                if(db.checkForSignUpType(type).equals("EMAIL") || db.checkForSignUpType(type).equals("PHONE")){
                    val username = view.etUsername.text.toString()
                    val password = view.etPassword.text.toString()
                    db.userEmail = username
                    db.userPassword = password
                    tvError.text = ""
                    presenter.login(null,"password",username,password,null)
                }else{
                    tvError.text = "Enter correct email or phone number"
                }
            }else{
                tvError.text = "Field cannot be empty"
            }

        }
        btnSignup.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.toRegister())
        }
        tvForgot.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.toReset())
        }

    }
    override fun clientInfo(clt: ClientInfo) {
        Log.i("MSG", clt.toString())
        db.setClientInfo(clt)
        changeDesign()
        activity?.window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressBar.visibility = View.GONE
    }

    override fun login(token: AccessToken) {
        db.token = token
        Log.i("MSG", "logges success->" + token)
        Toast.makeText(context, "logged in successfully", Toast.LENGTH_SHORT).show()
        presenterProfile.userInfo(db.token.tokenType + " " + db.token.accessToken)
    }

    override fun response(userInfo: UserInfo) {
        db.userInfo = userInfo
        db.haveUserInfo = true
        Log.i("MSG", "userInfo success->" + userInfo)
        findNavController().navigate(LoginFragmentDirections.toProfile())
    }

    override fun dataFlowWait() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleError(type: String) {
        if(type.contains("404")){
            tvError.text = "User not found"
        }else
            if(type.contains("403")){
                tvError.text = "Wrong password or pin"
            }else
                if(type.contains("connectionError")){
                    tvError.text = "Check your internet connection"
                }else
                tvError.text = "error type " + type
        Log.i("MSG", "logged error" + type)

    }

    override fun onDestroy() {
        presenter.destroy()
        presenterProfile.destroy()
        super.onDestroy()
    }
//------------------------------------------CHANGE DESIGN-------------------------------------------

    fun uploadLogoDesign(){
        Glide.with(this)
            .load(db.clientInfo.logoImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
    fun btnColorDesign(){
        if(db.clientInfo.buttonColor?.type=="gradient"){
            val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                db.clientInfo.buttonColor!!.colors.filter { it.isNotBlank() }.map { Color.parseColor(it) }.toIntArray()
            )
            btnSignin.background = gradientDrawable
        }else{
            btnSignin.setBackgroundColor(Color.parseColor(db.clientInfo.buttonColor!!.colors[0]))
        }
    }
    fun changeDesign() {
        uploadLogoDesign()
        btnColorDesign()
        loginpage.setBackgroundColor(
            Color.parseColor(db.clientInfo.backgroundColor))
    }

    fun showPassword() {
        if(etPassword.inputType == 129){
            Log.i("MSG", "show pass")
            etPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password,0,R.drawable.ic_visibility_off_black_24dp,0)
            etPassword.inputType = 144
        }else{
            Log.i("MSG", "hide pass")
            etPassword.inputType = 129
            etPassword.text = etPassword.text
            etPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password,0,R.drawable.ic_visibility_black_24dp,0)
        }

    }
}

