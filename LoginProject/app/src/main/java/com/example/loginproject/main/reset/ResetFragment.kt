package com.example.loginproject.main.reset

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.loginproject.MainActivity
import com.example.loginproject.MainActivity.Companion.db

import com.example.loginproject.R
import com.example.loginproject.data.interfaces.ResetView
import com.example.loginproject.data.presenter.ResetPresenter
import com.example.loginproject.main.register.RegisterFragmentDirections
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.imageViewReg
import kotlinx.android.synthetic.main.fragment_reset.*
import kotlinx.android.synthetic.main.fragment_reset.view.*

/**
 * A simple [Fragment] subclass.
 */
class ResetFragment : Fragment(), ResetView {
    val presenter = ResetPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeDesign()
        btnReset.setOnClickListener{
            val type = view.etUsername.text.toString()
            if(!db.isTimeLeft){
                if(MainActivity.db.checkForSignUpType(type).equals("EMAIL")){
                    presenter.resetRequest("email", db.userEmail)
                }else
                    if(MainActivity.db.checkForSignUpType(type).equals("PHONE")){
                        presenter.resetRequest("phone_number", db.userPhoneNumber)
                    }else{
                        view.tvError.text = "Enter correct email or phone number"
                    }
            }else{
                findNavController().navigate(ResetFragmentDirections.toVerify())
            }
        }
        tvSignin.setOnClickListener{
            findNavController().navigate(ResetFragmentDirections.toLogin())
        }
    }

    override fun response(str:String) {
        Log.i("MSG", "code sent true")
        db.verifyType = "reset"
        findNavController().navigate(ResetFragmentDirections.toVerify())
    }

    override fun dataFlowWait() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleError(type: String) {
        if(type.contains("404")){
            view?.tvError?.text = "User not found"
        }else{
            view?.tvError?.text = type
        }
        Log.i("MSG", type)
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
            btnReset.background = gradientDrawable
        }else{
            btnReset.setBackgroundColor(Color.parseColor(MainActivity.db.clientInfo.buttonColor!!.colors[0]))
        }
    }
    fun changeDesign() {
        uploadLogoDesign()
        btnColorDesign()
        resetpage.setBackgroundColor(
            Color.parseColor(MainActivity.db.clientInfo.backgroundColor))
    }



}
