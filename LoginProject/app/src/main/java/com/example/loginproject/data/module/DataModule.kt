package com.example.loginproject.data.module

import android.util.Log
import com.example.loginproject.data.interfaces.RemoteI
import com.example.loginproject.data.interfaces.RepoI
import com.example.loginproject.data.models.LoginRemoteDataSource
import com.example.loginproject.data.models.LoginRepository
import com.example.loginproject.data.network.ApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    factory<RepoI> { LoginRepository(get()) }
    factory<RemoteI> { LoginRemoteDataSource(get()) }
    single { generateApiService() }
}
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