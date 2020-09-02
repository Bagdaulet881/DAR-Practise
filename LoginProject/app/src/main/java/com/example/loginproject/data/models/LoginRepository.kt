package com.example.loginproject.data.models

import android.util.Log
import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.ClientInfo
import com.example.loginproject.data.network.TempToken
import com.example.loginproject.data.network.TempToken2
import io.reactivex.Completable
import io.reactivex.Observable

class LoginRepository {
    val remoteDataSource = LoginRemoteDataSource()

    fun getClientInfo():Observable<ClientInfo>{
        return remoteDataSource.downloadClientInfo()
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
}