package com.example.mvparticle.presenter

import com.example.mvparticle.model.ArticleRepository
import com.example.mvparticle.model.Post
import com.example.mvparticle.model.Student
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlePresenter(var view: ArticleView?){

    val repository = ArticleRepository()
    val disposable = CompositeDisposable()
    fun getStudents(){
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

    fun destroy(){
        disposable.dispose()
        view = null
    }

}

interface ArticleView{
    fun showStudents(students: List<Student>)
}