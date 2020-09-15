package com.example.loginproject.data.module

import com.example.loginproject.data.interfaces.Contract
import com.example.loginproject.data.presenter.LoginPresenter
import com.example.loginproject.data.presenter.ProfilePresenter
import com.example.loginproject.data.presenter.RegPresenter
import com.example.loginproject.data.presenter.ResetPresenter
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

val uiModule = module{

    factory<Contract.LoginPresenter> { (view: Contract.LoginView) -> LoginPresenter(view, get(), get()) }
    factory<Contract.ProfilePresenter> { (view: Contract.ProfileView) -> ProfilePresenter(view, get(), get()) }
    factory<Contract.RegPresenter> { (view: Contract.RegView) -> RegPresenter(view, get(), get()) }
    factory<Contract.ResetPresenter> { (view: Contract.ResetView) -> ResetPresenter(view, get(), get()) }


    factory { CompositeDisposable() }
}


