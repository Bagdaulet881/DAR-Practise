package com.example.loginproject.data.interfaces

import com.example.loginproject.data.network.AvatarInfo
import com.example.loginproject.data.network.UserInfo

interface ProfileView {
    fun userResponse(userInfo: UserInfo)
    fun avaResponse(avatarInfo: AvatarInfo)
    fun dataFlowWait()
    fun handleError(type: String)
}