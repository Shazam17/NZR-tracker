package com.example.nzr.modules.addCard

import com.example.nzr.data.rest.IStrategyFabric
import com.example.nzr.data.rest.models.GenericCardDetail
import com.example.nzr.data.rest.repository.IRepository
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable

interface IAddCardStrategy{
    fun addCard(id:String,name:String) : Observable<GenericCardDetail>
}

class GenericAddCardStrategy<Type:IRepository>(var repository: Type):IAddCardStrategy{
    override fun addCard(id: String, name: String): Observable<GenericCardDetail> {
        return repository.createCard(id,name)
    }
}

class AddCardStrategyFabric(var vendor:String) : IStrategyFabric<IAddCardStrategy> {

    override fun getYandexStrategy(): IAddCardStrategy {
        return GenericAddCardStrategy(YandexRepository())
    }

    override fun getTrelloStrategy(): IAddCardStrategy {
        return GenericAddCardStrategy(TrelloRepository())
    }

    fun getStrategy(): IAddCardStrategy?{
        when (vendor){
            "trello" -> return getTrelloStrategy()
            "yandex" -> return getYandexStrategy()
        }
        return null
    }
}