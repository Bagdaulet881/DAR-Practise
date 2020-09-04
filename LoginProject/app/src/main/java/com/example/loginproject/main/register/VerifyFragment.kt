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
import com.example.loginproject.data.interfaces.ResetView
import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.SmsCodeRequestBody
import com.example.loginproject.data.network.TempToken
import com.example.loginproject.data.presenter.RegPresenter
import com.example.loginproject.data.presenter.ResetPresenter
import io.reactivex.Completable
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.imageViewReg
import kotlinx.android.synthetic.main.fragment_verify.*
import kotlinx.android.synthetic.main.fragment_verify.view.*
import java.util.*


class VerifyFragment : Fragment() , RegView, ResetView{
    val presenter = RegPresenter(this)
    val presenterReset = ResetPresenter(this)

    lateinit var timer: CountDownTimer
    var seconds = 0
    lateinit var remainingMillis: Any
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
        tvTimer.text = "sending.."
        changeDesign()
        if (db.isTimeLeft){
            runFromSavedTimer()
//            true if user sent code already
        }else{
//            false its like a first start
            sendCode()
            seconds = db.timer.toInt()
        }

        btnVerify.setOnClickListener{
            if(etSms.text.isNotBlank()){
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
        Log.i("MSG", "onDestroy called")
        super.onDestroy()
    }
    fun sendCode(){
        if(db.verifyType == "reset"){
            if(db.isFirstStart){
                db.isFirstStart = false
                dataFlowWait()
            }else{
                if(db.typeOfRegister=="EMAIL"){
                    Log.i("MSG", "resend code" + db.userEmail)
                    presenterReset.resetRequest("email", db.userEmail)
                }else
                    presenterReset.resetRequest("phone_number", db.userPhoneNumber)
            }
        }else
            if(db.verifyType == "reg"){
                presenter.signUpPhone(db.userPhoneNumber)
            }
    }

    fun verifyCode(sid:String, code: String){

        if(db.verifyType == "reset"){
            presenterReset.resetVerifyCode(code)
        }else
            if(db.verifyType == "reg"){
                presenter.verifyCode(sid, code)
            }
    }

    override fun signUp(token: AccessToken) {
        Log.i("MSG", "signUp SID " + token)
    }

    override fun phoneVerify(temp: TempToken) {
        Log.i("MSG","VERIFY " + temp)
        timer.cancel()
        db.isTimeLeft = false
        tvTimer.text = "verified!"
        db.setTempTokenData(temp)
        findNavController().navigate(VerifyFragmentDirections.toConfirm())
    }

    override fun registerWithPassword(token: AccessToken) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun response(str:String) {

        if(str == "verified"){
            db.code = etSms.text.toString()
            Log.i("MSG", "toConfirm")
            timer.cancel()
            db.isTimeLeft = false
            findNavController().navigate(VerifyFragmentDirections.toConfirm())
        }else{
            Log.i("MSG", "code Resent true")
            dataFlowWait()
        }
    }

    override fun dataFlowWait() {
//        fun will work after getting "add_info" data from server
        seconds = db.timer.toInt()
        Log.i("MSG", "sendCode timer " + seconds)
        timer()
    }

    override fun handleError(type: String) {
        if(type.contains("500")){
            view?.tvError?.text = "Invalid or expired secret code"
        }else
        if (type.equals("pvError")){
            view?.tvError?.text = "wrong code try again"
        }else{
            view?.tvError?.text = type
        }
    }
//------------------------------------------TIMER---------------------------------------------------

    fun timer(){
            timer = object : CountDownTimer(seconds.toLong() * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    var sec = (millisUntilFinished / 1000)%60
                    remainingMillis = millisUntilFinished
                    var min = (millisUntilFinished / (1000*60))%60
                    var minStr = min.toString()
                    var secStr = sec.toString()
                    tvTimer.text = (minStr + " : " + secStr)
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
            db.isTimeLeft = true
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
