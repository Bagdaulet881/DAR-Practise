package com.example.loginproject.data.presenter

import android.util.Log
import com.example.loginproject.data.interfaces.ProfileView
import com.example.loginproject.data.models.LoginRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(var view: ProfileView?) {
    val repository = LoginRepository()
    val disposable = CompositeDisposable()

    fun userInfo(auth:String){
        Log.i("MSG", "auth used " + auth)

        val info = repository.getUserInfo(auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({userInfo->
                Log.i("MSG", "userInfo true")
                view?.response(userInfo)
            },{
                it.printStackTrace()
                view?.handleError(it.message!!)
                Log.i("MSG", "userInfo false")

            })
        disposable.add(info)
    }
    fun destroy(){
        disposable.dispose()
        view = null
    }
}