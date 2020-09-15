package com.example.loginproject.data.models

import android.util.Log
import com.example.loginproject.data.interfaces.RemoteI
import com.example.loginproject.data.interfaces.RepoI
import com.example.loginproject.data.network.*
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class LoginRepository (val remoteDataSource: RemoteI): RepoI{

    override fun getClientInfo():Observable<ClientInfo>{
        return remoteDataSource.downloadClientInfo()
    }
    override fun login(code:String?, type:String, username:String, password:String, refreshToken:String?):Observable<AccessToken>{
        return remoteDataSource.login(code,type,username,password,refreshToken)
    }
    override fun signUp(userrname:String, pwd:String?):Observable<AccessToken>{
        return remoteDataSource.signUp(userrname, pwd)
    }
    override fun signUpPhone(userrname:String):Observable<TempToken>{
        return remoteDataSource.signUpPhone(userrname)
    }
    override fun verify(sid:String, code:String):Completable{
        Log.i("MSG", "verify REPO")
        return remoteDataSource.verifyPhoneNumber(sid, code)
    }
    override fun verify2(sid:String, code:String):Observable<TempToken>{
        Log.i("MSG", "verify REPO")
        return remoteDataSource.verifyPhoneNumber2(sid, code)
    }
    override fun registerWithPwd(sid:String, pwd:String):Observable<AccessToken>{
        return remoteDataSource.registerWithPwd(sid, pwd)
    }
//-------------------------------------------RESET--------------------------------------------------
    override fun resetRequestOtp(reset_option:String, username:String):Observable<TempToken>{
        return remoteDataSource.requestOTP(reset_option, username)
    }
    override fun resetVerifyCode(sid: String,code: String):Observable<TempToken>{
        return remoteDataSource.resetVerify(sid, code)
    }
    override fun updatePassword(sid:String, newPassword:String):Observable<TempToken>{
        return remoteDataSource.updatePassword(sid, newPassword)
    }
//-------------------------------------------PROFILE------------------------------------------------
    override fun getUserInfo(authorization:String):Observable<UserInfo>{
        return remoteDataSource.getUserInfo(authorization)
    }
    override fun setUserInfo(multipart: MultipartBody.Part, authorization:String):Observable<AvatarInfo>{
        Log.i("MSG", "setUserInfo REPO")
        return remoteDataSource.setUserAva(multipart, authorization)
    }

}