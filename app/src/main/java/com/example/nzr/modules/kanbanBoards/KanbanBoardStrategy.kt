package com.example.nzr.modules.kanbanBoards

import com.example.nzr.data.rest.IStrategyFabric
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.data.rest.repository.IRepository
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable

interface IKanbanStrategy{
    var id:String
    fun fetchCards() : Observable<ArrayList<ArrayList<GenericCardShort>>>
}

class GenericKanbanStrategy<Type:IRepository>(var repository: Type,override var id:String):IKanbanStrategy{
    override fun fetchCards(): Observable<ArrayList<ArrayList<GenericCardShort>>> {
        return repository.fetchCardsById(id)
    }
}

class KanbanStrategyFabric(var map: Map<String,String>) : IStrategyFabric<IKanbanStrategy> {

    override fun getYandexStrategy() : IKanbanStrategy {
        return GenericKanbanStrategy(YandexRepository(),map["yandex"]!!)
    }

    override fun getTrelloStrategy() : IKanbanStrategy {
        return GenericKanbanStrategy(TrelloRepository(),map["trello"]!!)
    }

    fun getAllStrategies() : ArrayList<IKanbanStrategy>{
        var list = ArrayList<IKanbanStrategy>()
        map.forEach { vendor, id ->
            when(vendor){
                "trello" -> list.add(getTrelloStrategy())
                "yandex" -> list.add(getYandexStrategy())
            }
        }
        return list
    }
}