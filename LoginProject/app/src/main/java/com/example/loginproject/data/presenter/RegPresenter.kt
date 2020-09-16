package com.example.loginproject.data.presenter

import android.util.Log
import com.example.loginproject.MainActivity.Companion.db
import com.example.loginproject.data.interfaces.Contract
import com.example.loginproject.data.interfaces.RepoI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegPresenter(var view: Contract.RegView?,
                   val disposable: CompositeDisposable,
                   private val repository: RepoI
):Contract.RegPresenter {

    override fun signUp(u:String,p:String?){
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
    override fun signUpPhone(u:String){
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
    override fun verifyCode(sid:String, code:String){
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
    override fun registerWithPwd(sid:String, pwd:String){

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
    override fun destroy(){
        disposable.dispose()
        view = null
    }
}