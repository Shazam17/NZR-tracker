package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.GenericBoardShort
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import kotlin.collections.ArrayList

interface IDepartmentStrategy{
    fun fetchBoards() : Observable<ArrayList<GenericBoardShort>>
}

interface IKanbanStrategy{
    var id:String
    fun fetchCards() : Observable<ArrayList<ArrayList<GenericCardShort>>>
}

interface IAddCardStrategy{
    fun addCard() : Observable<GenericCardShort>
}

interface IStrategyFabric<Type>{
    fun getYandexStrategy():Type
    fun getTrelloStrategy():Type
    fun getAllStrategies():ArrayList<Type>
}

class DepartmentYandexStrategy : IDepartmentStrategy{

    var yandexRepository = YandexRepository()
    override fun fetchBoards() : Observable<ArrayList<GenericBoardShort>> {
        var list = ArrayList<GenericBoardShort>()
        return yandexRepository.fetchBoards()
    }
}

class DepartmentTrelloStrategy : IDepartmentStrategy{
    var trelloRepository = TrelloRepository()
    override fun fetchBoards() : Observable<ArrayList<GenericBoardShort>> {
        var list = ArrayList<GenericBoardShort>()
        return trelloRepository.fetchBoards()
    }
}

class DepartmentStrategyFabric : IStrategyFabric<IDepartmentStrategy>{


    override fun getYandexStrategy() : IDepartmentStrategy{
        return DepartmentYandexStrategy()
    }

    override fun getTrelloStrategy() : IDepartmentStrategy{
        return DepartmentTrelloStrategy()
    }

    override fun getAllStrategies() : ArrayList<IDepartmentStrategy>{
        var list = ArrayList<IDepartmentStrategy>()

        list.add(getYandexStrategy())
        list.add(getTrelloStrategy())

        return list
    }
}


class KanbanYandexStrategy(override var id:String) : IKanbanStrategy{
    var yandexRepository = YandexRepository()
    override fun fetchCards(): Observable<ArrayList<ArrayList<GenericCardShort>>> {
       return yandexRepository.fetchCardsById(id)
    }
}

class KanbanTrelloStrategy(override var id:String) : IKanbanStrategy{
    var trelloRepository = TrelloRepository()
    override fun fetchCards(): Observable<ArrayList<ArrayList<GenericCardShort>>> {
        return trelloRepository.fetchCardsById(id)
    }
}

class KanbanStrategyFabric(var map: Map<String,String>) : IStrategyFabric<IKanbanStrategy>{

    override fun getYandexStrategy() : IKanbanStrategy {
        return KanbanYandexStrategy(map["yandex"]!!)
    }

    override fun getTrelloStrategy() : IKanbanStrategy {
        return KanbanTrelloStrategy(map["trello"]!!)
    }
    override fun getAllStrategies() : ArrayList<IKanbanStrategy>{
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

class AddCardTrelloStrategy : IAddCardStrategy{
    var trelloRepository = TrelloRepository()
    override fun addCard(): Observable<GenericCardShort> {
        return trelloRepository.createCard(id,name)
    }
}

class AddCardYandexStrategy : IAddCardStrategy{
    var yandexRepository = YandexRepository()
    override fun addCard(): Observable<GenericCardShort> {
       return yandexRepository.createCard(name,id)
    }
}

class AddCardStrategyFabric() : IStrategyFabric<IAddCardStrategy>{

    override fun getYandexStrategy(): IAddCardStrategy {
        return AddCardYandexStrategy()
    }

    override fun getTrelloStrategy(): IAddCardStrategy {
        return AddCardTrelloStrategy()
    }

    override fun getAllStrategies(): ArrayList<IAddCardStrategy> {
        var list = ArrayList<IAddCardStrategy>()

        list.add(getYandexStrategy())
        list.add(getTrelloStrategy())

        return list
    }

    fun getStrategy(vendorId:Int): IAddCardStrategy{

    }
}
