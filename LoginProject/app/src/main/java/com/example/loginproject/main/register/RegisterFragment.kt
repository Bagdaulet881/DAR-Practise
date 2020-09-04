package com.example.loginproject.main.register

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.loginproject.MainActivity

import com.example.loginproject.R
import kotlinx.android.synthetic.main.fragment_register.*
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.text.buildSpannedString
import com.example.loginproject.MainActivity.Companion.db

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeDesign()
        btnSignin.setOnClickListener{
            findNavController().navigate(RegisterFragmentDirections.toLogin())
        }
        btnNext.setOnClickListener{
            val type = etUsername.text.toString()
            if(checkbox.isChecked){
                if(db.checkForSignUpType(type).equals("EMAIL")){
                    findNavController().navigate(RegisterFragmentDirections.toConfirm())
                }else
                    if(db.checkForSignUpType(type).equals("PHONE")){
                        db.verifyType = "reg"
                        findNavController().navigate(RegisterFragmentDirections.toVerify())
                    }else{
                        tvError.text = "Enter correct email or phone number"
                    }
            }else{
                tvError.text = "Check box terms and conditions"
            }

        }
    }
    fun openWebView(url: String){
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }
//------------------------------------------CHANGE DESIGN-------------------------------------------

    fun termsTextDesign(){
        val spanned = SpannableString ("I agree with terms and conditions")
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(context, "Opening..", Toast.LENGTH_SHORT).show()
                openWebView(db.clientInfo.termsConditionUrl!!)
            }
        }
        spanned.setSpan(clickableSpan,13,33,0)
        spanned.setSpan(ForegroundColorSpan(Color.RED), 13,33,0)
        tvConditions.movementMethod = LinkMovementMethod.getInstance()
        tvConditions.text = spanned
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
                MainActivity.db.clientInfo.buttonColor!!.colors.filter { it.isNotBlank() }.map { Color.parseColor(it) }.toIntArray()
            )
            btnNext.background = gradientDrawable
        }else{
            btnNext.setBackgroundColor(Color.parseColor(MainActivity.db.clientInfo.buttonColor!!.colors[0]))
        }
    }
    fun changeDesign() {
        uploadLogoDesign()
        btnColorDesign()
        termsTextDesign()

        regpage.setBackgroundColor(
            Color.parseColor(MainActivity.db.clientInfo.backgroundColor))
    }
}
