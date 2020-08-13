package com.example.listfiltering.presenter

import com.example.listfiltering.`interface`.ArticleContract
import com.example.listfiltering.`interface`.ArticleRemoteI
import com.example.listfiltering.`interface`.ArticleRepoI
import com.example.listfiltering.model.ArticleRemoteDataSource
import com.example.listfiltering.model.ArticleRepository

class Provider {
    fun getArticlePresenter(view: ArticleContract.View): ArticleContract.Presenter{
        return ArticlePresenter(view, getArticleRepository())
    }

    fun getArticleRepository(): ArticleRepoI{
        return ArticleRepository(getArticleRemote())
    }

    fun getArticleRemote(): ArticleRemoteI{
        return ArticleRemoteDataSource()
    }
}