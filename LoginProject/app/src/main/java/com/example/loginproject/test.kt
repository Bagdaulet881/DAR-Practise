package com.example.loginproject

import java.util.*

fun saveTimer(){
    var date = Calendar.getInstance()
    var time = date.timeInMillis
    var future = time + 2*60000
    println(time)
}

fun main(){
    saveTimer()
}