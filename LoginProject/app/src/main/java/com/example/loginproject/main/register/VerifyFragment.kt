package com.example.loginproject.main.register

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.loginproject.MainActivity
import com.example.loginproject.R
import com.example.loginproject.data.interfaces.RegView
import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.SmsCodeRequestBody
import com.example.loginproject.data.presenter.RegPresenter
import io.reactivex.Completable
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.imageViewReg
import kotlinx.android.synthetic.main.fragment_verify.*
import kotlinx.android.synthetic.main.fragment_verify.view.*


class VerifyFragment : Fragment() , RegView{
    val presenter = RegPresenter(this)

    lateinit var timer: CountDownTimer
    var Seconds = 120
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeDesign()
        timer()
        btnVerify.setOnClickListener{
            if(etSms.text.isNotBlank()){
                timer.cancel()
                tvTimer.text = "verified!"
                verifyCode(etSms.text.toString())

            }else{
                view.tvError.text = "Empty field"
            }
        }
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }
    fun verifyCode(code: String){
        presenter.verifyCode(code)
    }
    fun timer(){
            timer = object : CountDownTimer(Seconds.toLong() * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var sec = (millisUntilFinished / 1000)%60

                    var min = (millisUntilFinished / (1000*60))%60

                    tvTimer.setText(min.toString() + " : " + sec.toString())
                }

                override fun onFinish() {
                    tvTimer.setText("RESEND?")
                }
        }.start()
    }
    fun uploadLogoDesign(){
        Glide.with(this)
            .load(MainActivity.db.clientInfo.logoImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageViewReg)
    }
    fun btnColorDesign(){
        if(MainActivity.db.clientInfo.buttonColor?.type=="gradient"){
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                MainActivity.db.clientInfo.buttonColor!!.colors.filter { it.isNotBlank() }.map { Color.parseColor(it) }.toIntArray()
            )
            btnVerify.background = gradientDrawable
        }else{
            btnNext.setBackgroundColor(Color.parseColor(MainActivity.db.clientInfo.buttonColor!!.colors[0]))
        }
    }
    fun changeDesign() {
        uploadLogoDesign()
        btnColorDesign()
        verifypage.setBackgroundColor(
            Color.parseColor(MainActivity.db.clientInfo.backgroundColor))
    }

    override fun signUp(token: AccessToken) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun phoneVerify(cmp: Completable) {
        Log.i("MSG","VERIFY " + cmp)
    }


}
