package com.example.loginproject.data.interfaces

import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.ClientInfo

interface LoginView {
    fun clientInfo(clt: ClientInfo)
    fun login(token: AccessToken)
    fun handleError(type: String)
}