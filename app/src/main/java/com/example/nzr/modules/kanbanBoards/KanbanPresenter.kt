package com.example.nzr.modules.kanbanBoards

import android.util.Log
import com.example.nzr.data.rest.models.cardShort
import com.example.nzr.data.rest.models.listsCards
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import com.example.nzr.modules.startScreen.RXPresenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign

class KanbanPresenter(var view :KanbanContract.KanbanView) : KanbanContract.KanbanPresenter, RXPresenter(){

    var lists : MutableList<listsCards> = ArrayList()

    override fun getTrelloListId(position:Int): String {
        if(lists.size != 0){
            return lists[position].id
        }else{
            return ""
        }
    }

    override fun fetch() {
        if(view.getTrelloBoardId() != null && view.getYandexBoardId() != null ){
            subscriptions += TrelloRepository()
                .fetchCardsById(view.getTrelloBoardId()!!)
                .concatMap {
                    it.body()!!.forEach { list-> list.cards.forEach{card-> card.vendor = true} }
                    lists.addAll(it.body()!!)
                    view.initPagerAdapter(lists)
                    YandexRepository()
                        .fetchCards(mapOf("queue" to view.getYandexBoardId()!!))
                }.concatMap{
                    Log.d("fetchRep",it.body().toString())
                    if(it.body() != null){
                        var  lsOpen : MutableList<cardShort> = ArrayList()
                        it.body()!!.forEach { task -> if(task.status.key.equals("open")) lsOpen.add(cardShort(task.id,task.summary,false))}
                        lists[0].cards.addAll(lsOpen)

                        var lsNeedInformation : MutableList<cardShort> = ArrayList()
                        it.body()!!.forEach { task -> if(task.status.key.equals("needInfo")) lsNeedInformation.add(cardShort(task.id,task.summary,false))}
                        lists[1].cards.addAll(lsNeedInformation)

                        var lsInWork : MutableList<cardShort> = ArrayList()
                        it.body()!!.forEach { task -> if(task.status.key.equals("inProgress")) lsInWork.add(cardShort(task.id,task.summary,false))}
                        lists[2].cards.addAll(lsInWork)

                        var lsClosed : MutableList<cardShort> = ArrayList()
                        it.body()!!.forEach { task -> if(task.status.key.equals("resolved")) lsClosed.add(cardShort(task.id,task.summary,false))}
                        lists[3].cards.addAll(lsClosed)
                        view.initPagerAdapter(lists)
                        view.setRefresh(false)
                    }
                    Observable.just(null)
                }.subscribe({

                },{
                })
        }
    }

    override fun updateList() {
        lists.clear()
        if(view.getTrelloBoardId() != null && view.getYandexBoardId() != null){
            fetch()
        }else{
            if(view.getTrelloBoardId() != null){
                fetchListsRepTrello()
            }
            if(view.getYandexBoardId() != null){
                fetchListsRepYandex()
            }
        }

    }

    override fun fetchListsRepTrello(){
        subscriptions += TrelloRepository()
            .fetchCardsById(view.getTrelloBoardId()!!)
            .subscribe({
                lists.addAll(it.body()!!)
                it.body()!!.forEach { list-> list.cards.forEach{card-> card.vendor = true} }
                view.initPagerAdapter(lists)
                view.setRefresh(false)
            },{
                Log.d("fetchRep","errorr")
            })
    }

    override fun fetchListsRepYandex() {
        Log.d("fetchRep",view.getYandexBoardId()!!)
        subscriptions += YandexRepository()
            .fetchCards(mapOf("queue" to view.getYandexBoardId()!!))
            .subscribe({
                    var  ls : MutableList<cardShort> = ArrayList()
                    if(it.body() != null){
                        it.body()!!.forEach { ls.add(cardShort(it.id,it.summary,false))
                        Log.d("fetchRep",it.summary)}
                    }
                    var list :listsCards = listsCards("id","name", ls)
                    lists.add(list)
                    view.initPagerAdapter(lists)
                view.setRefresh(false)
            },{

                })
    }

}