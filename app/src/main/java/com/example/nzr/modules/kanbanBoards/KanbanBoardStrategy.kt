package com.example.nzr.modules.kanbanBoards

import com.example.nzr.data.rest.IKanbanStrategy
import com.example.nzr.data.rest.IStrategyFabric
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable

class KanbanYandexStrategy(override var id:String) : IKanbanStrategy {
    var yandexRepository = YandexRepository()
    override fun fetchCards(): Observable<ArrayList<ArrayList<GenericCardShort>>> {
        return yandexRepository.fetchCardsById(id)
    }
}

class KanbanTrelloStrategy(override var id:String) : IKanbanStrategy {
    var trelloRepository = TrelloRepository()
    override fun fetchCards(): Observable<ArrayList<ArrayList<GenericCardShort>>> {
        return trelloRepository.fetchCardsById(id)
    }
}

class KanbanStrategyFabric(var map: Map<String,String>) : IStrategyFabric<IKanbanStrategy> {

    override fun getYandexStrategy() : IKanbanStrategy {
        return KanbanYandexStrategy(map["yandex"]!!)
    }

    override fun getTrelloStrategy() : IKanbanStrategy {
        return KanbanTrelloStrategy(map["trello"]!!)
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