package com.example.nzr.modules.CardDetailActivity

import android.util.Log
import android.view.View
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import com.example.nzr.modules.startScreen.RXPresenter
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_card_detail.*

class CardDetailPresenter(var view: CardDetailContract.CardDetailView) : CardDetailContract.CardDetailPresenter,RXPresenter(){



    override fun fetchCardByIdTrello(id:String){
        subscriptions += TrelloRepository()
            .fetchCardById(id)
            .subscribe(
                {
                    Log.d("fetchCardDetail",it.body().toString())
                    view.initViews(it.body()?.name!!,it.body()?.desc!!)

                },{
                    Log.d("fetchCardDetail",it.localizedMessage!!)
                })
    }

    override fun fetchCardByIdYandex(id: String) {
        subscriptions += YandexRepository()
            .fetchCardById(id)
            .subscribe({
                view.initViews(it.body()?.summary!!,"desc")
                },{
                    Log.d("fetchCardDetail",it.localizedMessage!!)
                })
    }
}