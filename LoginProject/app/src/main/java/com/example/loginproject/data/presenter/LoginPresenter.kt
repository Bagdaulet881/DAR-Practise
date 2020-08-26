package com.example.loginproject.data.presenter

import com.example.loginproject.data.interfaces.LoginView
import com.example.loginproject.data.models.LoginRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter(var view: LoginView?) {
    val repository = LoginRepository()
    val disposable = CompositeDisposable()

    fun getClientInfo(){
        val info = repository.getClientInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({clt->
                view?.clientInfo(clt)
            },{
                it.printStackTrace()
            })
        disposable.add(info)
    }
    fun destroy(){
        disposable.dispose()
        view = null
    }


}