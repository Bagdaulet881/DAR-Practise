package com.example.loginproject.data.presenter

import android.util.Log
import com.example.loginproject.data.interfaces.Contract
import com.example.loginproject.data.interfaces.RepoI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter(var view: Contract.LoginView?,
                     val disposable: CompositeDisposable,
                     private val repository: RepoI
):Contract.LoginPresenter {

    override fun getClientInfo(){
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

    override fun login(code:String?, type:String, username:String, password:String, refreshToken:String?){
        val info = repository.login(code,type,username,password,refreshToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({login->
                Log.i("MSG", "logged true")
                view?.login(login)
            },{
                it.printStackTrace()
                view?.handleError(it.message!!)
                Log.i("MSG", "logged false" + it)

            })
        disposable.add(info)
    }

    override fun destroy(){
        disposable.dispose()
        view = null
    }


}