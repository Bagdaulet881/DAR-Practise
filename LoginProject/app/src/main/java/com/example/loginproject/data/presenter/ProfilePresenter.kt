package com.example.loginproject.data.presenter

import android.util.Log
import com.example.loginproject.MainActivity.Companion.db
import com.example.loginproject.data.interfaces.ProfileView
import com.example.loginproject.data.models.LoginRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

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
                db.haveUserInfo = true
                view?.userResponse(userInfo)

            },{
                it.printStackTrace()
                view?.handleError(it.message!!)
                Log.i("MSG", "userInfo false")

            })
        disposable.add(info)
    }
    fun userAva(multipartBody: MultipartBody.Part, auth:String){
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
    fun userAva2(multipartBody: HashMap<String, RequestBody>, auth:String){
        Log.i("MSG", "auth used " + auth)

        val info = repository.setUserInfo2(multipartBody, auth)
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
    fun destroy(){
        disposable.dispose()
        view = null
    }
}