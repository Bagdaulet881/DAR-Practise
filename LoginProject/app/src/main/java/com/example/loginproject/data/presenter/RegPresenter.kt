package com.example.loginproject.data.presenter

import android.util.Log
import com.example.loginproject.MainActivity.Companion.db
import com.example.loginproject.data.interfaces.RegView
import com.example.loginproject.data.models.LoginRepository
import com.example.loginproject.data.network.TempToken
import com.example.loginproject.data.network.TempToken2
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegPresenter(var view: RegView?) {
    val repository = LoginRepository()
    val disposable = CompositeDisposable()

    fun signUp(u:String,p:String?){
        val info = repository.signUp(u,p)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("MSG","presenter " + it)
                view?.signUp(it)
                Log.i("MSG","presenter singup subscribe1")

            },{
                it.printStackTrace()
                view?.handleError(it.message.toString())
            })
        disposable.add(info)
    }
    fun signUpPhone(u:String){
        val info = repository.signUpPhone(u)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("MSG","presenter " + it)
                db.setTempTokenData(it)
                view?.dataFlowWait()
            },{
                it.printStackTrace()
            })
        disposable.add(info)
    }
    fun verifyCode(sid:String, code:String){
        Log.i("MSG","phone Verified!0" + sid + ", " + code)


        val info = repository.verify2(sid,code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("MSG","phone Verified! " + it)
                view?.phoneVerify(it)
            },{
                it.printStackTrace()
                view?.handleError("pvError")
                Log.i("MSG","phone Verified! ERROR ")
            })
        disposable.add(info)
    }
    fun registerWithPwd(sid:String, pwd:String){

        val info = repository.registerWithPwd(sid, pwd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("MSG","phone Verified!")
                view?.signUp(it)
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