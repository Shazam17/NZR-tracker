package com.example.nzr.modules.addCard

import com.example.nzr.data.rest.IStrategyFabric
import com.example.nzr.data.rest.models.GenericCardDetail
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable

interface IAddCardStrategy{
    fun addCard(id:String,name:String) : Observable<GenericCardDetail>
}

class AddCardTrelloStrategy : IAddCardStrategy{
    var trelloRepository = TrelloRepository()
    override fun addCard(id:String,name:String): Observable<GenericCardDetail> {
        return trelloRepository.createCard(id,name)
    }
}

class AddCardYandexStrategy : IAddCardStrategy{
    var yandexRepository = YandexRepository()
    override fun addCard(id:String,name:String): Observable<GenericCardDetail> {
        return yandexRepository.createCard(name,id)
    }
}

class AddCardStrategyFabric(var vendor:String) : IStrategyFabric<IAddCardStrategy> {

    override fun getYandexStrategy(): IAddCardStrategy {
        return AddCardYandexStrategy()
    }

    override fun getTrelloStrategy(): IAddCardStrategy {
        return AddCardTrelloStrategy()
    }

    fun getStrategy(): IAddCardStrategy?{
        when (vendor){
            "trello" -> return getTrelloStrategy()
            "yandex" -> return getYandexStrategy()
        }
        return null
    }
}