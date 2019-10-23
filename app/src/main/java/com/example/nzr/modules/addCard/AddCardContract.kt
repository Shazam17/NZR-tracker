package com.example.nzr.modules.addCard

interface CreateCardContract{


    interface AddCardPresenter{
        fun createCard(name:String,id: String, vendor: Boolean)
    }

    interface AddCardView{
        fun getCardId():String
        fun back()
    }

    interface AddCardModel{

    }

}