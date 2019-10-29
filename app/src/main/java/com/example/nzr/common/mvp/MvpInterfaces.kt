package com.example.nzr.common.mvp

import android.app.Activity
import android.view.View
import io.reactivex.disposables.CompositeDisposable


abstract class RXPresenter{
    val subscriptions: CompositeDisposable = CompositeDisposable()
}
interface IView{
    fun getActivity(): Activity
    fun initViews()
}


interface IPresnter<in V :IView>{
    //fun takeView(v: View)
    //fun onViewAtached()
    //fun dropView()
    //fun onViewDetached()
}