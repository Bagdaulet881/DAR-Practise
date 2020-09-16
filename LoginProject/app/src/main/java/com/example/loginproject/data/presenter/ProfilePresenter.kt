package com.example.loginproject.data.presenter

import android.util.Log
import com.example.loginproject.MainActivity.Companion.db
import com.example.loginproject.data.interfaces.Contract
import com.example.loginproject.data.interfaces.RepoI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class ProfilePresenter(var view: Contract.ProfileView?,
                       val disposable: CompositeDisposable,
                       private val repository: RepoI
):Contract.ProfilePresenter {

    override fun userInfo(auth:String){
        Log.i("MSG", "auth used " + auth)

        val info = repository.getUserInfo(auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({userInfo->
                Log.i("MSG", "userInfo true")
                db.haveUserInfo = true
                view?.userResponse(userInfo)


            },{
                it.printStackTrace()
                view?.handleError(it.message!!)
                Log.i("MSG", "userInfo false")

            })
        disposable.add(info)
    }
    override fun userAva(multipartBody: MultipartBody.Part, auth:String){
        Log.i("MSG", "auth used " + auth)

        val info = repository.setUserInfo(multipartBody, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({avaInfo->
                Log.i("MSG", "avaInfo true")
                view?.avaResponse(avaInfo)
            },{
                it.printStackTrace()
                view?.handleError(it.message!!)
                Log.i("MSG", "avaInfo false")

            })
        disposable.add(info)
    }

    override fun destroy(){
        disposable.dispose()
        view = null
    }
}