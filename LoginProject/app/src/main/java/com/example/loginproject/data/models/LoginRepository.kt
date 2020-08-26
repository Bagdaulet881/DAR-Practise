package com.example.loginproject.data.models

import com.example.loginproject.data.network.ClientInfo
import io.reactivex.Observable

class LoginRepository {
    val remoteDataSource = LoginRemoteDataSource()

    fun getClientInfo():Observable<ClientInfo>{
        return remoteDataSource.downloadClientInfo()
    }
}