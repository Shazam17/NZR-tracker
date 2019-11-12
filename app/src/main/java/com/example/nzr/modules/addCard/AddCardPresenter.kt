package com.example.nzr.modules.addCard

import android.util.Log
import android.widget.Toast
import com.example.nzr.common.mvp.RXPresenter
import com.example.nzr.data.rest.AddCardStrategyFabric
import com.example.nzr.data.rest.IAddCardStrategy
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.rxkotlin.plusAssign

class CreateCardPresenter(var view:CreateCardContract.AddCardView) :CreateCardContract.AddCardPresenter,
    RXPresenter(){

    lateinit var strategy: IAddCardStrategy

    override fun createCard(vendorId:Int) {

        var addCardStrategyFabric = AddCardStrategyFabric()
        var tag =
        strategy = addCardStrategyFabric.getStrategy(vendorId)


        subscriptions += strategy.addCard()
            .subscribe({
                Toast.makeText(view.getActivity(),"create success",Toast.LENGTH_SHORT).show()
                view.back()
            },{
                Toast.makeText(view.getActivity(),"create success",Toast.LENGTH_SHORT).show()
            })
    }
}