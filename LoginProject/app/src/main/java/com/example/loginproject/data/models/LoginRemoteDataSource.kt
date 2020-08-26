package com.example.loginproject.data.models

import com.example.loginproject.data.network.API
import com.example.loginproject.data.network.ClientInfo
import io.reactivex.Observable
import java.util.*

class LoginRemoteDataSource {
    val client_id = "5ea469f9-a517-427b-8b16-290e9e57bb7f"
    val client_secret = "1rg3Y9cseLsyqolScMQ1kYD8flnLYtqFFkQc36D2WPDdnkXs1BqLuq83yDhkfjtH"

    fun downloadClientInfo():Observable<ClientInfo>{
        return API.apiService.getClientInfo(client_id,client_secret)
    }
}