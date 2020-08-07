package com.example.listfiltering.`interface`

import com.example.listfiltering.model.Post
import com.example.listfiltering.Student
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface PostsService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("posts")
    fun getPostsObs(): Single<List<Post>>

    @GET("sough/students/students")
    fun getStudentsObs(): Single<List<Student>>



}