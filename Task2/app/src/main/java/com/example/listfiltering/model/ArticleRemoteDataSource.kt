package com.example.listfiltering.model

import com.example.listfiltering.API
import com.example.listfiltering.`interface`.ArticleRemoteI
import com.example.listfiltering.`interface`.PostsService
import io.reactivex.Single
import retrofit2.Call

class ArticleRemoteDataSource(private val service: PostsService): ArticleRemoteI {
    fun downloadPosts(): Call<List<Post>> {
        return service.getPosts()
    }

    fun downloadPostsObs(): Single<List<Post>> {
        return service.getPostsObs()
    }

    override fun downloadStudentsObs(): Single<List<Student>> {
        return service.getStudentsObs()
    }
}