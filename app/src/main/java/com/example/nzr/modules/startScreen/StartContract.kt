package com.example.nzr.modules.startScreen

import android.app.Activity
import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.models.GenericBoardShort
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import io.reactivex.disposables.CompositeDisposable


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

}

