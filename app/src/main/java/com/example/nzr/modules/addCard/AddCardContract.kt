package com.example.nzr.modules.addCard

import com.example.nzr.common.mvp.IView

interface CreateCardContract{


    interface AddCardPresenter{
        fun createCard(vendorId:Int)
    }

    interface AddCardView : IView{
        fun back()
    }

    interface AddCardModel{

    }

}