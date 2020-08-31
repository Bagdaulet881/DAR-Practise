package com.example.loginproject.data.interfaces

import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.ClientInfo

interface LoginView {
    fun clientInfo(clt: ClientInfo)
}