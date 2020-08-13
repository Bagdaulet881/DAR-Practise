package com.example.listfiltering.model

import com.example.listfiltering.API
import com.example.listfiltering.`interface`.ArticleRemoteI
import io.reactivex.Single
import retrofit2.Call

class ArticleRemoteDataSource: ArticleRemoteI {
    fun downloadPosts(): Call<List<Post>> {
        return API.postsService.getPosts()
    }

    fun downloadPostsObs(): Single<List<Post>> {
        return API.postsService.getPostsObs()
    }

    override fun downloadStudentsObs(): Single<List<Student>> {
        return API.postsService.getStudentsObs()
    }
}