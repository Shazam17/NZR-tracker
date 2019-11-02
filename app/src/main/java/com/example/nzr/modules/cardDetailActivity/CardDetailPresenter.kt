package com.example.nzr.modules.cardDetailActivity

import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.example.nzr.common.mvp.RXPresenter
import com.example.nzr.data.rest.repository.IRepository
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_card_detail.*

class CardDetailPresenter(var view: CardDetailContract.CardDetailView,var pair: Pair<String, String>) : CardDetailContract.CardDetailPresenter, RXPresenter(){

    lateinit var repository: IRepository
    var rbList: ArrayList<String>()

    init{
        when(pair.first){
            "trello" -> repository = TrelloRepository()
            "yandex" -> repository = YandexRepository()
        }
    }

    override fun fetch() {
        Log.d("CardDetailPresenter","${pair.first}  ${pair.second}")

        subscriptions += repository
            .fetchCardById(pair.second)
            .subscribe({
                view.initViews(it.name,"desc")
            },{
                Log.d("fetchCardDetail",it.localizedMessage!!)
            })
        subscriptions += repository
            .fetchTranistions(pair.second)
            .subscribe({
                it.forEachIndexed{index,transition ->
                    var button = RadioButton(this)
                    button.id = index
                    button.text = transition.id
                    rbList.add(transition.id!!)
                    view.addRadionButton(button)
                }
            },{
                Log.d("fetchCardDetail",it.localizedMessage!!)
            })


        }

    override fun moveToClosed(inString:String) {

        repository.move()

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




    override fun move() {

    }
}