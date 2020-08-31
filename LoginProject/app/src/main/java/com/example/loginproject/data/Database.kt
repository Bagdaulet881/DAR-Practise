package com.example.loginproject.data

import com.example.loginproject.data.network.ClientInfo
import java.util.regex.Pattern

class Database {
    lateinit var clientInfo: ClientInfo
    lateinit var userEmail: String
    lateinit var userPhoneNumber: String
    lateinit var userPassword: String
    fun getClientInfo(): Any{
        return clientInfo
    }
    fun setClientInfo(clt: Any){
        clientInfo = clt as ClientInfo
    }
//TODO sharedPref

    private fun isValidMail(email: String): Boolean {
        val EMAIL_STRING = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return Pattern.compile(EMAIL_STRING).matcher(email).matches()
    }

    private fun isValidMobile(phone: String): Boolean {
        return if (!Pattern.matches("[a-zA-Z]+", phone)) {
            phone.length > 6 && phone.length <= 13
        } else false
//        CHECK IT AGAIN TODO
    }

    fun checkForSignUpType(type:String):String{
        if(isValidMail(type)){
            userEmail = type
            return "EMAIL"
        }else
            if (isValidMobile(type)){
                userPhoneNumber = type
                return "PHONE"
            }else
                return "Incorrect type"
    }
}