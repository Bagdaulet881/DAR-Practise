package com.example.loginproject.data.models

import android.util.Log
import com.example.loginproject.data.network.*
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class LoginRepository {
    val remoteDataSource = LoginRemoteDataSource()

    fun getClientInfo():Observable<ClientInfo>{
        return remoteDataSource.downloadClientInfo()
    }
    fun login(code:String?, type:String, username:String, password:String, refreshToken:String?):Observable<AccessToken>{
        return remoteDataSource.login(code,type,username,password,refreshToken)
    }
    fun signUp(userrname:String, pwd:String?):Observable<AccessToken>{
        return remoteDataSource.signUp(userrname, pwd)
    }
    fun signUpPhone(userrname:String):Observable<TempToken>{
        return remoteDataSource.signUpPhone(userrname)
    }
    fun verify(sid:String, code:String):Completable{
        Log.i("MSG", "verify REPO")
        return remoteDataSource.verifyPhoneNumber(sid, code)
    }
    fun verify2(sid:String, code:String):Observable<TempToken>{
        Log.i("MSG", "verify REPO")
        return remoteDataSource.verifyPhoneNumber2(sid, code)
    }
    fun registerWithPwd(sid:String, pwd:String):Observable<AccessToken>{
        return remoteDataSource.registerWithPwd(sid, pwd)
    }
//-------------------------------------------RESET--------------------------------------------------
    fun resetRequestOtp(reset_option:String, username:String):Observable<TempToken>{
        return remoteDataSource.requestOTP(reset_option, username)
    }
    fun resetVerifyCode(sid: String,code: String):Observable<TempToken>{
        return remoteDataSource.resetVerify(sid, code)
    }
    fun updatePassword(sid:String, newPassword:String):Observable<TempToken>{
        return remoteDataSource.updatePassword(sid, newPassword)
    }
//-------------------------------------------PROFILE------------------------------------------------
    fun getUserInfo(authorization:String):Observable<UserInfo>{
        return remoteDataSource.getUserInfo(authorization)
    }
    fun setUserInfo(multipart: MultipartBody.Part, authorization:String):Observable<AvatarInfo>{
        Log.i("MSG", "setUserInfo REPO")
        return remoteDataSource.setUserAva(multipart, authorization)
    }
//    fun setUserInfo(multipart: MultipartBody.Part, folder: RequestBody, name: RequestBody, authorization:String):Observable<AvatarInfo>{
//        Log.i("MSG", "setUserInfo REPO")
//        return remoteDataSource.setUserAva(multipart,folder, name, authorization)
//    }
    fun setUserInfo2(multipart: HashMap<String, RequestBody>, authorization:String):Observable<AvatarInfo>{
        Log.i("MSG", "setUserInfo REPO")
        return remoteDataSource.setUserAva2(multipart, authorization)
    }
}