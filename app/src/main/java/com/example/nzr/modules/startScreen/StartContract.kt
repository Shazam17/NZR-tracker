package com.example.nzr.modules.startScreen

import android.app.Activity
import android.content.Context
import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.models.board
import com.example.nzr.data.rest.models.genericBoardShort
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import io.reactivex.disposables.CompositeDisposable

abstract class RXPresenter{
    val subscriptions: CompositeDisposable = CompositeDisposable()
}

interface StartContract{


    interface StartView : IView{
        fun toNextScreen()
        override fun getActivity():Activity
        fun getCode() : String?
    }

    interface StartPresenter{
        fun handleSignInResult(completedTask: Task<GoogleSignInAccount>)
        fun signIn()
    }

    interface StartModel{

    }

}

interface DepartmentContract{

    interface DepartmentView{
        fun initAdapter(list:MutableList<genericBoardShort>)
        fun updateAdapter(depList:MutableList<genericBoardShort>)
    }

    interface  DepartmentPresenter{
        fun fetchDepartments()
    }

    interface DepartmentModel{

    }


}