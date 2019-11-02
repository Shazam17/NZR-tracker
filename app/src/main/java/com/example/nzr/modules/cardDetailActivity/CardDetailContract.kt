package com.example.nzr.modules.cardDetailActivity

import android.widget.RadioButton
import com.example.nzr.common.mvp.IView

interface CardDetailContract {

    interface CardDetailPresenter{
        fun move(index:Int)
        fun fetch()
    }

    interface CardDetailView : IView{
        fun initViews(name : String , desc : String)
        fun back()
        fun addRadioButton(button: RadioButton)
    }

}