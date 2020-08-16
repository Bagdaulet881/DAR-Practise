package com.example.listfiltering.presenter

import android.util.Log
import com.example.listfiltering.model.Student
import com.example.listfiltering.`interface`.ArticleContract
import com.example.listfiltering.`interface`.ArticleRepoI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ArticlePresenter( var view: ArticleContract.View?,
                        val disposable: CompositeDisposable,
                       private val repository: ArticleRepoI
                       ): ArticleContract.Presenter{

    override fun getStudents(){
//        repository.getPosts().enqueue(object: Callback<List<Post>>{
//
//            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//                view?.showPosts(response.body()!!)
//            }
//
//
//            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//
//        })
        Log.i("MSG", "Article Presenter")
        val students = repository.getStudentsObs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({students->
                view?.showStudents(students)
            }, {
                it.printStackTrace()
            })
        disposable.add(students)
    }

    override fun destroy(){
        disposable.dispose()
        view = null
    }

}

interface ArticleView{
    fun showStudents(students: List<Student>)
}