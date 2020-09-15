package com.example.loginproject

import android.app.Application
import com.example.loginproject.data.module.dataModule
import com.example.loginproject.data.module.uiModule
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(uiModule, dataModule))
        }
    }
}