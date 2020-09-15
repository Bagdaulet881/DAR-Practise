package com.example.loginproject.data.models

import android.util.Log
import com.example.loginproject.data.interfaces.RemoteI
import com.example.loginproject.data.network.*
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*

class LoginRemoteDataSource(private val apiService: ApiService): RemoteI {
    val client_id = "5ea469f9-a517-427b-8b16-290e9e57bb7f"
    val client_secret = "1rg3Y9cseLsyqolScMQ1kYD8flnLYtqFFkQc36D2WPDdnkXs1BqLuq83yDhkfjtH"

    override fun downloadClientInfo():Observable<ClientInfo>{
        return apiService.getClientInfo(client_id,client_secret)
    }
    override fun login(code:String?, type:String, username:String, password:String, refreshToken:String?):Observable<AccessToken>{
        return apiService.grantNewAccessToken(code,type,client_id,client_secret,username,password,refreshToken)
    }
    override fun signUp(username:String, pwd:String?):Observable<AccessToken>{
        return apiService.signUp("application/json", NewUserBody(client_id, client_secret,username,pwd))
    }
    override fun signUpPhone(username:String):Observable<TempToken>{
        return apiService.signUpPhone("application/json", NewUserBody(client_id, client_secret,username))
    }
    override fun verifyPhoneNumber(sid:String, code:String):Completable{
        return apiService.phoneVerify(sid, SmsCodeRequestBody(code))
    }
    override fun verifyPhoneNumber2(sid:String, code:String):Observable<TempToken>{
        return apiService.phoneVerify2(sid, SmsCodeRequestBody(code))
    }
    override fun registerWithPwd(sid:String, pwd:String):Observable<AccessToken>{
        return apiService.registerWithPassword(sid, PasswordRequestBody(pwd))
    }
//-------------------------------------------RESET--------------------------------------------------

    override fun requestOTP(reset_option:String, username:String):Observable<TempToken>{
        return apiService.requestOTP(resetBody(client_id,reset_option,username))
    }
    override fun resetVerify(sid: String, code:String):Observable<TempToken>{
        return apiService.resetVerify(sid, code, client_id)
    }
    override fun updatePassword(sid:String, newPassword:String):Observable<TempToken>{

        return apiService.updatePassword(sid, NewPasswordBody(client_id, newPassword))
    }
//-------------------------------------------PROFILE------------------------------------------------
    override fun getUserInfo(authorization:String):Observable<UserInfo>{
        return apiService.getUserInfo(authorization)
    }
    override fun setUserAva(multipart: MultipartBody.Part, authorization:String):Observable<AvatarInfo>{
        return apiService.setUserAva(multipart, authorization)
    }

    override fun setUserAva2(multipart: HashMap<String, RequestBody>, authorization:String):Observable<AvatarInfo>{
        return apiService.setUserAva2(authorization, multipart)
    }
}