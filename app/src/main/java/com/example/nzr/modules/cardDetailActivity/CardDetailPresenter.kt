package com.example.nzr.modules.cardDetailActivity

import android.util.Log
import android.widget.Toast
import com.example.nzr.common.mvp.RXPresenter
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign

class CardDetailPresenter(var view: CardDetailContract.CardDetailView) : CardDetailContract.CardDetailPresenter, RXPresenter(){


    var yandex = YandexRepository()

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

    override fun moveToClosed(id: String, type: String) {
        if(view.getVendor()){
            //trello
            var trello = TrelloRepository()
            subscriptions += trello
                .updateCard(id,type)
                .subscribe({
                    Log.d("fetchCardDetail","success")
                    Toast.makeText(view.getActivity(),"success moving",Toast.LENGTH_SHORT).show()
                    view.back()
                },{
                    Log.d("fetchCardDetail",it.localizedMessage!!)
                    Toast.makeText(view.getActivity(),"Erorr in moving Card ${it.localizedMessage!!}",Toast.LENGTH_SHORT).show()
                })



        }else{
            //yandex

            subscriptions += yandex
                .fetchTransitionsById(id)
                .concatMap {
                    if(it.isSuccessful){
                        var typeId : String = it.body()!!.find { transition -> transition.to.key == type }?.id?:"no"
                        yandex.moveCard(id,typeId)
                    }else{
                        Observable.just(null)
                    }
                }.subscribe({
                    if(it!!.isSuccessful){
                        Log.d("fetchCardDetail","success")
                        Toast.makeText(view.getActivity(),"success moving",Toast.LENGTH_SHORT).show()
                        view.back()
                    }
                },{
                    Log.d("fetchCardDetail",it.localizedMessage!!)
                    Toast.makeText(view.getActivity(),"Erorr in moving Card ${it.localizedMessage!!}",Toast.LENGTH_SHORT).show()
                })

        }
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

    override fun move() {

    }
}