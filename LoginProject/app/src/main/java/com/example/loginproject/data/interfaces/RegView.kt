package com.example.loginproject.data.interfaces

import com.example.loginproject.data.network.*
import io.reactivex.Completable

interface RegView {
    fun signUp(token:AccessToken)
    fun phoneVerify(temp: TempToken)
    fun registerWithPassword(token:AccessToken)
    fun dataFlowWait()
    fun handleError(type: String)
}