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
    var rbList =  ArrayList<String>()

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
                    var button = RadioButton(view.getActivity())
                    button.id = index
                    button.text = transition.text
                    rbList.add(transition.id!!)
                    view.addRadioButton(button)
                }
            },{
                Log.d("fetchCardDetail",it.localizedMessage!!)
            })


        }

    override fun move(index:Int) {
        subscriptions += repository
            .move(pair.second,rbList[index])
            .subscribe({
                Log.d("fetchCardDetail", "success")
                Toast
                    .makeText(view.getActivity(), "success moving", Toast.LENGTH_SHORT)
                    .show()
                view.back()
            }, {
                Log.d("fetchCardDetail", it.localizedMessage!!)
                Toast
                    .makeText(
                    view.getActivity(),
                    "Error in moving Card ${it.localizedMessage!!}",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }
}
