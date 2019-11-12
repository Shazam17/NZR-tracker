package com.example.nzr.modules.kanbanBoards

import android.util.Log
import com.example.nzr.common.mvp.RXPresenter
import com.example.nzr.data.rest.IKanbanStrategy
import com.example.nzr.data.rest.models.GenericCardShort


class KanbanPresenter(var view :KanbanContract.KanbanView) : KanbanContract.KanbanPresenter, RXPresenter(){

    var lists : ArrayList<ArrayList<GenericCardShort>> = ArrayList()

    var strategyList : ArrayList<IKanbanStrategy> = ArrayList()

    override fun fetch(ids:MutableMap<String,String>) {
        var kanbanFabric = KanbanStrategyFabric(ids)
        lists.clear()
        view.setRefresh(true)
        strategyList = kanbanFabric.getAllStrategies()

        strategyList.forEach {  strategy ->
            strategy.fetchCards()
                .subscribe({
                    lists.addAll(it)
                    view.initPagerAdapter(lists)
                    view.setRefresh(false)
                },{
                    Log.d("KanbanPresenter","error ${it.localizedMessage}")
                })
        }
    }


}