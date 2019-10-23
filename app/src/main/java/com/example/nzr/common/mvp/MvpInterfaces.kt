package com.example.nzr.common.mvp

import android.app.Activity
import android.view.View


interface IView{
    fun getActivity(): Activity
}


interface IPresnter<in V :IView>{
    //fun takeView(v: View)
    //fun onViewAtached()
    //fun dropView()
    //fun onViewDetached()
}