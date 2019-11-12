package com.example.nzr.modules.addCard

import android.widget.Toast
import com.example.nzr.common.mvp.RXPresenter
import io.reactivex.rxkotlin.plusAssign

class CreateCardPresenter(var view:CreateCardContract.AddCardView,var vendors : ArrayList<String>) :CreateCardContract.AddCardPresenter,
    RXPresenter(){

    lateinit var strategy: IAddCardStrategy


    override fun createCard(vendorId:Int,map:Map<String,String>,name:String) {
        var addCardStrategyFabric = AddCardStrategyFabric(vendors[vendorId])
        strategy = addCardStrategyFabric.getStrategy()!!


        subscriptions += strategy.addCard(id = map[vendors[vendorId]]?:"",name = name)
            .subscribe({
                Toast.makeText(view.getActivity(),"create success",Toast.LENGTH_SHORT).show()
                view.back()
            },{
                Toast.makeText(view.getActivity(),"create success",Toast.LENGTH_SHORT).show()
            })
    }
}