package com.example.listfiltering.model

import com.example.listfiltering.`interface`.ArticleRemoteI
import com.example.listfiltering.`interface`.ArticleRepoI
import io.reactivex.Single

class ArticleRepository(val remoteDataSource: ArticleRemoteI): ArticleRepoI {

//    fun getPosts(): Call<List<Post>> {
//        return remoteDataSource.downloadPosts()
//    }
//
//    fun getPostsObs(): Single<List<Post>> {
//        return remoteDataSource.downloadPostsObs()
//    }

    override fun getStudentsObs(): Single<List<Student>> {
        return remoteDataSource.downloadStudentsObs()
    }
}