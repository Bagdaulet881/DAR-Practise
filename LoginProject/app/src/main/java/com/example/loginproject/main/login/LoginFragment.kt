package com.example.loginproject.main.login

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
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


class LoginFragment : Fragment(), LoginView {

    val presenter = LoginPresenter(this)
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

        btnSignup.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.toRegister())
        }

    }
    override fun clientInfo(clt: ClientInfo) {
        Log.i("MSG", clt.toString())
        db.setClientInfo(clt)
        changeDesign()

    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }
    fun uploadLogoDesign(){
        Glide.with(this)
            .load(db.clientInfo.logoImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
    fun btnColorDesign(){
        if(db.clientInfo.buttonColor?.type=="gradient"){
            val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
//                intArrayOf(
//                    Color.parseColor(db.clientInfo.buttonColor!!.colors[0]),
//                    Color.parseColor(db.clientInfo.buttonColor!!.colors[1]),
//                    Color.parseColor(db.clientInfo.buttonColor!!.colors[2])
//                )
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

