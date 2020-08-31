package com.example.loginproject.data.interfaces

import com.example.loginproject.data.network.AccessToken
import com.example.loginproject.data.network.NewUserBody
import com.example.loginproject.data.network.PasswordRequestBody
import com.example.loginproject.data.network.SmsCodeRequestBody
import io.reactivex.Completable

interface RegView {
    fun signUp(token:AccessToken)
    fun phoneVerify(cmp: Completable)
//    fun registerWithPassword(pwd: PasswordRequestBody)
}