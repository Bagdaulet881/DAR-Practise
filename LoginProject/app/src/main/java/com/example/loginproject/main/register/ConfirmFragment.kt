package com.example.loginproject.main.register

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.loginproject.MainActivity
import com.example.loginproject.MainActivity.Companion.db

import com.example.loginproject.R
import com.example.loginproject.data.interfaces.RegView
import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.SmsCodeRequestBody
import com.example.loginproject.data.presenter.RegPresenter
import io.reactivex.Completable
import kotlinx.android.synthetic.main.fragment_confirm.*
import kotlinx.android.synthetic.main.fragment_confirm.btnNext
import kotlinx.android.synthetic.main.fragment_confirm.imageViewReg
import kotlinx.android.synthetic.main.fragment_confirm.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*


class ConfirmFragment : Fragment() , RegView{
    val presenter = RegPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNext.setOnClickListener{
            if(checkForPassword(etCreatePass1.text.toString(),etCreatePass2.text.toString())){
                db.userPassword = etCreatePass1.text.toString()
                Toast.makeText(context, db.userEmail + " - " + db.userPassword, Toast.LENGTH_SHORT).show()

                presenter.signUp(db.userEmail, db.userPassword)
                Log.i("MSG","presenter singup")
            }else{
                view.tvError.text = "Check your password"
            }
        }
        etCreatePass1.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= etCreatePass1.getRight() - etCreatePass1.getCompoundDrawables().get(
                        DRAWABLE_RIGHT
                    ).getBounds().width()
                ) {
                    showPassword(v as EditText)
                    return@OnTouchListener true
                }
            }
            false
        })
        etCreatePass2.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= etCreatePass2.getRight() - etCreatePass2.getCompoundDrawables().get(
                        DRAWABLE_RIGHT
                    ).getBounds().width()
                ) {
                    showPassword(v as EditText)
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }
    fun checkForPassword(p1:String,p2:String):Boolean{
        if(p1.equals(p2)){
            return true
        }else
            return false
    }
    fun showPassword(v:EditText) {
        if(v.inputType == 129){
            Log.i("MSG", "show pass")
            v.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password,0,R.drawable.ic_visibility_off_black_24dp,0)
            v.inputType = 144
        }else{
            Log.i("MSG", "hide pass")
            v.inputType = 129
            v.text = v.text
            v.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password,0,R.drawable.ic_visibility_black_24dp,0)
        }

    }

    override fun signUp(token: AccessToken) {
        Log.i("MSG","presenter singup213")

        Log.i("MSG", "token getting " + token.tokenType)
    }

    override fun phoneVerify(cmp: Completable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun uploadLogoDesign(){
        Glide.with(this)
            .load(db.clientInfo.logoImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageViewReg)
    }
    fun btnColorDesign(){
        if(MainActivity.db.clientInfo.buttonColor?.type=="gradient"){
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
//                intArrayOf(
//                    Color.parseColor(db.clientInfo.buttonColor!!.colors[0]),
//                    Color.parseColor(db.clientInfo.buttonColor!!.colors[1]),
//                    Color.parseColor(db.clientInfo.buttonColor!!.colors[2])
//                )
                db.clientInfo.buttonColor!!.colors.filter { it.isNotBlank() }.map { Color.parseColor(it) }.toIntArray()
            )
            btnNext.background = gradientDrawable
        }else{
            btnNext.setBackgroundColor(Color.parseColor(MainActivity.db.clientInfo.buttonColor!!.colors[0]))
        }
    }
    fun changeDesign() {
        uploadLogoDesign()
        btnColorDesign()

        regpage2.setBackgroundColor(
            Color.parseColor(MainActivity.db.clientInfo.backgroundColor))
    }

}
