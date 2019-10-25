package com.example.nzr.modules.addCard

import com.example.nzr.common.mvp.IView

interface CreateCardContract{


    interface AddCardPresenter{
        fun createCard(name:String,id: String, vendor: Boolean)
    }

    interface AddCardView : IView{
        fun getCardId():String
        fun back()
    }

    interface AddCardModel{

    }

}