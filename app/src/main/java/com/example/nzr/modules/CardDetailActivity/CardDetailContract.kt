package com.example.nzr.modules.CardDetailActivity

interface CardDetailContract {

    interface CardDetailPresenter{
        fun fetchCardByIdTrello(id:String)
        fun fetchCardByIdYandex(id:String)
    }

    interface CardDetailView{
        fun initViews(name : String , desc : String)
    }

    interface CardDetailModel{

    }
}