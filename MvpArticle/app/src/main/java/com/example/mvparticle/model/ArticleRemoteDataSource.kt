package com.example.mvparticle.model

import com.example.mvparticle.API
import io.reactivex.Single
import retrofit2.Call

class ArticleRemoteDataSource {
    fun downloadPosts(): Call<List<Post>> {
        return API.postsService.getPosts()
    }

    fun downloadPostsObs(): Single<List<Post>> {
        return API.postsService.getPostsObs()
    }

    fun downloadStudentsObs(): Single<List<Student>> {
        return API.postsService.getStudentsObs()
    }
}