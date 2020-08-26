package com.example.loginproject.data.network

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object API {
    val apiService: ApiService = generateApiService()

    private fun generateApiService() : ApiService {
        Log.i("MSG", "generateApiService check")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://griffon.dar-dev.zone")
            .client(getHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun getHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

}