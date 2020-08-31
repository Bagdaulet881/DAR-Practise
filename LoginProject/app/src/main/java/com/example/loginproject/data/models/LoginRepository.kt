package com.example.loginproject.data.models

import android.util.Log
import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.ClientInfo
import io.reactivex.Completable
import io.reactivex.Observable

class LoginRepository {
    val remoteDataSource = LoginRemoteDataSource()

    fun getClientInfo():Observable<ClientInfo>{
        return remoteDataSource.downloadClientInfo()
    }

    fun signUp(userrname:String, pwd:String):Observable<AccessToken>{
        return remoteDataSource.signUp(userrname, pwd)
    }

    fun verify(code:String):Completable{
        return remoteDataSource.verifyPhoneNumber(code)
    }
}