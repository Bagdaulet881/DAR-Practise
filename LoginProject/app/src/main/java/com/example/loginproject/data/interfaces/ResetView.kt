package com.example.loginproject.data.interfaces

interface ResetView {
    fun response(str: String)
    fun dataFlowWait()
    fun handleError(type: String)
}