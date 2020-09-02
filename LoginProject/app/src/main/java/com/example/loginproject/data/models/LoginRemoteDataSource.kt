package com.example.loginproject.data.models

import android.util.Log
import com.example.loginproject.data.network.*
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

class LoginRemoteDataSource {
    val client_id = "5ea469f9-a517-427b-8b16-290e9e57bb7f"
    val client_secret = "1rg3Y9cseLsyqolScMQ1kYD8flnLYtqFFkQc36D2WPDdnkXs1BqLuq83yDhkfjtH"

    fun downloadClientInfo():Observable<ClientInfo>{
        return API.apiService.getClientInfo(client_id,client_secret)
    }

    fun signUp(username:String, pwd:String?):Observable<AccessToken>{

        return API.apiService.signUp("application/json", NewUserBody(client_id, client_secret,username,pwd))
    }
    fun signUpPhone(username:String):Observable<TempToken>{

        return API.apiService.signUpPhone("application/json", NewUserBody(client_id, client_secret,username))
    }
    fun verifyPhoneNumber(sid:String, code:String):Completable{
        return API.apiService.phoneVerify(sid, SmsCodeRequestBody(code))
    }
    fun verifyPhoneNumber2(sid:String, code:String):Observable<TempToken>{
        return API.apiService.phoneVerify2(sid, SmsCodeRequestBody(code))
    }
    fun registerWithPwd(sid:String, pwd:String):Observable<AccessToken>{
        return API.apiService.registerWithPassword(sid, PasswordRequestBody(pwd))
    }
}