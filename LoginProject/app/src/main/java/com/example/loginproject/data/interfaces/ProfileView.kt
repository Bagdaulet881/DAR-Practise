package com.example.loginproject.data.interfaces

import com.example.loginproject.data.network.UserInfo

interface ProfileView {
    fun response(userInfo: UserInfo)
    fun dataFlowWait()
    fun handleError(type: String)
}