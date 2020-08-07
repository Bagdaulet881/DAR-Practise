package com.example.mvparticle

import android.util.Log
import com.example.mvparticle.`interface`.PostsService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object API {

    val postsService: PostsService = generatePostsService()

    private fun generatePostsService() : PostsService {
        Log.i("MSG", "generatePostsService")
        val retrofit = Retrofit.Builder()
//            .baseUrl("https://jsonplaceholder.typicode.com")
            .baseUrl("https://my-json-server.typicode.com")
            .client(getHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        return retrofit.create(PostsService::class.java)
    }

    private fun getHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}

