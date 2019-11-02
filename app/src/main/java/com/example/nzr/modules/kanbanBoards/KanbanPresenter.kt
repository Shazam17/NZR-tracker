package com.example.nzr.modules.kanbanBoards

import android.util.Log
import com.example.nzr.common.mvp.RXPresenter
import com.example.nzr.data.rest.models.CardShort
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.data.rest.models.ListsCards
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign

class KanbanPresenter(var view :KanbanContract.KanbanView) : KanbanContract.KanbanPresenter, RXPresenter(){

    var lists : ArrayList<ArrayList<GenericCardShort>> = ArrayList()

    override fun fetch(ids:MutableMap<String,String>) {
        lists.clear()
        ids.forEach{ vendor, id ->
            when(vendor){
                "trello"-> {
                    TrelloRepository()
                        .fetchCardsById(id)
                        .subscribe({
                            lists.addAll(it)
                            view.initPagerAdapter(lists)
                            view.setRefresh(false)
                        },{
                            Log.d("KanbanPresenter","error ${it.localizedMessage}")
                        })
                }
                "yandex"-> {
                    YandexRepository()
                        .fetchCardsById(id)
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
    }


}