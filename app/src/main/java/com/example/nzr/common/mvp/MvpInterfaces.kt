package com.example.nzr.common.mvp

import android.view.View

interface IView{

}


interface IPresnter<in V :IView>{
    fun takeView(v: View)
    fun onViewAtached()
    fun dropView()
    fun onViewDetached()
}