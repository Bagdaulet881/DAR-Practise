package com.example.listfiltering.module

import android.util.Log
import com.example.listfiltering.API
import com.example.listfiltering.`interface`.ArticleRemoteI
import com.example.listfiltering.`interface`.ArticleRepoI
import com.example.listfiltering.`interface`.PostsService
import com.example.listfiltering.model.ArticleRemoteDataSource
import com.example.listfiltering.model.ArticleRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    factory<ArticleRepoI> { ArticleRepository(get()) }
    factory<ArticleRemoteI> { ArticleRemoteDataSource(get()) }
    single { generatePostsService() }

}
private fun generatePostsService() : PostsService {
    Log.i("MSG", "generatePostsService")
    val retrofit = Retrofit.Builder()
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