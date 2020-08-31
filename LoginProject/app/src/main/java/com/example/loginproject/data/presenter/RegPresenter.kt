package com.example.loginproject.data.presenter

import android.util.Log
import com.example.loginproject.data.interfaces.RegView
import com.example.loginproject.data.models.LoginRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegPresenter(var view: RegView?) {
    val repository = LoginRepository()
    val disposable = CompositeDisposable()

    fun signUp(u:String,p:String){
        Log.i("MSG","presenter singup subscribe2")

        val info = repository.signUp(u,p)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({acc->
                Log.i("MSG","presenter " + acc)
                view?.signUp(acc)
                Log.i("MSG","presenter singup subscribe1")

            },{
                it.printStackTrace()
            })
        disposable.add(info)
    }
    fun verifyCode(code:String){

        val info = repository.verify(code)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                view?.phoneVerify(it)
//                Log.i("MSG","presenter singup subscribe1")
//
//            },{
//                it.printStackTrace()
//            })
//        disposable.add(info)
        view?.phoneVerify(info)
    }
    fun destroy(){
        disposable.dispose()
        view = null
    }
}