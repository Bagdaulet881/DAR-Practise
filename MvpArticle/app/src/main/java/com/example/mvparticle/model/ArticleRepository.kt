package com.example.mvparticle.model

import io.reactivex.Single
import retrofit2.Call

class ArticleRepository {
    val remoteDataSource = ArticleRemoteDataSource()

    fun getPosts(): Call<List<Post>> {
        return remoteDataSource.downloadPosts()
    }

    fun getPostsObs(): Single<List<Post>> {
        return remoteDataSource.downloadPostsObs()
    }

    fun getStudentsObs(): Single<List<Student>> {
        return remoteDataSource.downloadStudentsObs()
    }
}