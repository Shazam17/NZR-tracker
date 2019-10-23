package com.example.nzr.modules.CardDetailActivity

interface CardDetailContract {

    interface CardDetailPresenter{
        fun fetchCardByIdTrello(id:String)
        fun fetchCardByIdYandex(id:String)
        fun moveToClosed(id:String,type:String)
    }

    interface CardDetailView{
        fun initViews(name : String , desc : String)
        fun getVendor():Boolean
    }

    interface CardDetailModel{

    }
}