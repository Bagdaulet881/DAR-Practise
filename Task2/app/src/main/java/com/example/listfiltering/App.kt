package com.example.listfiltering

import android.app.Application
import com.example.listfiltering.module.dataModule
import com.example.listfiltering.module.uiModule
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(uiModule, dataModule))
        }
    }
}