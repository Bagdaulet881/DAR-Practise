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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.loginproject.MainActivity
import com.example.loginproject.MainActivity.Companion.db
import com.example.loginproject.R
import com.example.loginproject.data.interfaces.RegView
import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.SmsCodeRequestBody
import com.example.loginproject.data.network.TempToken
import com.example.loginproject.data.presenter.RegPresenter
import io.reactivex.Completable
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.imageViewReg
import kotlinx.android.synthetic.main.fragment_verify.*
import kotlinx.android.synthetic.main.fragment_verify.view.*
import java.util.*


class VerifyFragment : Fragment() , RegView{
    val presenter = RegPresenter(this)

    lateinit var timer: CountDownTimer
    var seconds = 0
    lateinit var remainingMillis: Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendCode()
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
        if (db.isTimeLeft){
            runFromSavedTimer()
//            true if user sent code already
        }else{
//            false its like a first start
            seconds = db.timer.toInt()
        }

        btnVerify.setOnClickListener{
            if(etSms.text.isNotBlank()){
                timer.cancel()
                tvTimer.text = "verified!"
                verifyCode(db.sid, etSms.text.toString())

            }else{
                view.tvError.text = "Empty field"
            }
        }
        btnResend.setOnClickListener{
            btnResend.visibility = View.INVISIBLE
            sendCode()
        }
    }

    override fun onDestroy() {
        saveTimer()
        timer.cancel()
        super.onDestroy()
    }
    fun sendCode(){
        presenter.signUpPhone(db.userPhoneNumber)
        seconds = db.timer.toInt()
        Log.i("MSG", "sendCode timer " + seconds)
        Thread.sleep(2000L)
        timer()
    }

    fun verifyCode(sid:String, code: String){
        presenter.verifyCode(sid, code)
    }

    override fun signUp(token: AccessToken) {
        Log.i("MSG", "signUp SID " + token)
    }

    override fun phoneVerify(temp: TempToken) {
        Log.i("MSG","VERIFY " + temp)
        db.setTempTokenData(temp)
        findNavController().navigate(VerifyFragmentDirections.toConfirm())
    }

    override fun registerWithPassword(token: AccessToken) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun timer(){
            timer = object : CountDownTimer(seconds.toLong() * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    var sec = (millisUntilFinished / 1000)%60
                    remainingMillis = millisUntilFinished
                    var min = (millisUntilFinished / (1000*60))%60

                    tvTimer.text = (min.toString() + " : " + sec.toString())
                }

                override fun onFinish() {
                    btnResend.visibility = View.VISIBLE
                }
        }.start()
    }
    fun saveTimer(){
        var date = Calendar.getInstance()
        var currentTime = date.timeInMillis
        var sec = remainingMillis as Long/1000
        var deadline = currentTime/1000 + sec
        db.remainingTime = deadline.toInt()
        db.isTimeLeft = true
    }
    fun runFromSavedTimer(){
        var date = Calendar.getInstance()
        var currentTime = date.timeInMillis/1000
        if((db.remainingTime-currentTime.toInt())<0){
            Log.i("MSG", "RESEND TIMER")
            db.isTimeLeft = false
//            seconds = 1
            btnResend.visibility = View.INVISIBLE
        }else
        {
            var secs = db.remainingTime-currentTime.toInt()
            Log.i("MSG", "TIMER " + secs)
            db.isTimeLeft = false
            seconds = secs
            timer()
        }
    }
//------------------------------------------CHANGE DESIGN-------------------------------------------

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
            btnVerify.setBackgroundColor(Color.parseColor(MainActivity.db.clientInfo.buttonColor!!.colors[0]))
        }
    }
    fun changeDesign() {
        uploadLogoDesign()
        btnColorDesign()
        verifypage.setBackgroundColor(
            Color.parseColor(MainActivity.db.clientInfo.backgroundColor))
    }




}
