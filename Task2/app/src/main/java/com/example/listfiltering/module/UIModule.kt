package com.example.listfiltering.module

import com.example.listfiltering.`interface`.ArticleContract
import com.example.listfiltering.presenter.ArticlePresenter
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

val uiModule = module{

    factory<ArticleContract.Presenter> {(view: ArticleContract.View) -> ArticlePresenter(view, get(), get())}
//    scope<BlankFragment>{
//        scoped{(view: ArticleContract.View) -> ArticlePresenter(view, get(), get()) as ArticleContract.Presenter}
//    }

    factory { CompositeDisposable() }
}


