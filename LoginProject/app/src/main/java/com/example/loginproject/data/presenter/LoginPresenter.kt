package com.example.loginproject.data.presenter

import android.util.Log
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
                view?.handleError("connectionError")
            })
        disposable.add(info)
    }

    fun login(code:String?, type:String, username:String, password:String, refreshToken:String?){
        val info = repository.login(code,type,username,password,refreshToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({login->
                Log.i("MSG", "logged true")
                view?.login(login)
            },{
                it.printStackTrace()
                view?.handleError(it.message!!)
                Log.i("MSG", "logged false")

            })
        disposable.add(info)
    }

    fun destroy(){
        disposable.dispose()
        view = null
    }


}