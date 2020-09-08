package com.example.loginproject.data.presenter

import android.util.Log
import com.example.loginproject.MainActivity.Companion.db
import com.example.loginproject.data.interfaces.ResetView
import com.example.loginproject.data.models.LoginRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ResetPresenter(var view: ResetView?){
    val repository = LoginRepository()
    val disposable = CompositeDisposable()

    fun resetRequest(resetOption:String,username:String){
        val info = repository.resetRequestOtp(resetOption, username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("MSG","presenter " + it)
                db.setTempTokenData(it)
                view?.response("")
            },{
                it.printStackTrace()
                Log.i("MSG", "resend code ERROR")
                view?.handleError(it.message.toString())
            })
        disposable.add(info)
    }

    fun resetVerifyCode(sid:String, code:String){
        val info = repository.resetVerifyCode(sid,code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("MSG","resetVerifyCode TRUE " + it)
                db.setTempTokenData(it)
                view?.response("verified")
            },{
                it.printStackTrace()
                view?.handleError(it.message.toString())
                Log.i("MSG","resetVerifyCode FALSE ")
            })
        disposable.add(info)
    }

    fun updatePassword(sid:String, newPassword:String){
        val info = repository.updatePassword(sid, newPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("MSG","password updated true " + it)
                view?.response("updated")
            },{
                it.printStackTrace()
                view?.handleError(it.message.toString())
                Log.i("MSG","password updated false" + it.message.toString() + "sid used " + sid + " " + newPassword)

            })
        disposable.add(info)
    }
}