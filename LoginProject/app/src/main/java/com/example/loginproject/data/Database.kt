package com.example.loginproject.data

import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.ClientInfo
import com.example.loginproject.data.network.TempToken
import java.util.regex.Pattern

class Database {
    lateinit var clientInfo: ClientInfo
    lateinit var userEmail: String
    lateinit var userPhoneNumber: String
    lateinit var userPassword: String
    lateinit var typeOfRegister: String
    lateinit var token: AccessToken
    lateinit var sid: String
    var timer = "20"
    lateinit var tempToken: TempToken
    var remainingTime = 0
    var isTimeLeft = false


    fun setTempTokenData(temp: TempToken){
        tempToken = temp
        if (!temp.add_info.isNullOrBlank()){
            timer = temp.add_info
        }
        sid = temp.sid
    }

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
            typeOfRegister = "EMAIL"
            return "EMAIL"
        }else
            if (isValidMobile(type)){
                userPhoneNumber = type
                typeOfRegister = "PHONE"
                return "PHONE"
            }else
                return "Incorrect type"
    }
}