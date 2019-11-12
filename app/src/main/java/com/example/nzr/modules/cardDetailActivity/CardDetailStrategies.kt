package com.example.nzr.modules.cardDetailActivity

import com.example.nzr.data.rest.IStrategyFabric
import com.example.nzr.data.rest.models.GenericCardDetail
import com.example.nzr.data.rest.models.GenericTransition
import com.example.nzr.data.rest.models.Transition
import com.example.nzr.data.rest.repository.IRepository
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable


interface ICardDetailStrategy{
    fun fetchCard(id:String): Observable<GenericCardDetail>
    fun fetchTransitions(id:String):Observable<ArrayList<GenericTransition>>
    fun move(id: String,idList:String): Observable<GenericCardDetail>
}


class CardDetailYandexStrategy : ICardDetailStrategy{
    var repository = YandexRepository()
    override fun fetchCard(id:String):Observable<GenericCardDetail> {
        return repository.fetchCardById(id)
    }
    override fun fetchTransitions(id: String): Observable<ArrayList<GenericTransition>> {
        return repository.fetchTranistions(id)
    }

    override fun move(id: String, idList: String): Observable<GenericCardDetail> {
        return repository.move(id ,idList)
    }
}

class CardDetailTrelloStrategy : ICardDetailStrategy{
    var repository = TrelloRepository()
    override fun fetchCard(id:String):Observable<GenericCardDetail> {
        return repository.fetchCardById(id)
    }
    override fun fetchTransitions(id: String): Observable<ArrayList<GenericTransition>> {
        return repository.fetchTranistions(id)
    }

    override fun move(id: String,idList:String): Observable<GenericCardDetail> {
        return repository.move(id,idList)
    }
}

class CardDetailStrategiesFabric : IStrategyFabric<ICardDetailStrategy>{
    override fun getYandexStrategy(): ICardDetailStrategy {
        return CardDetailYandexStrategy()
    }

    override fun getTrelloStrategy(): ICardDetailStrategy {
        return CardDetailTrelloStrategy()
    }

    fun getStrategy(vendor:String): ICardDetailStrategy?{
        when(vendor){
            "trello" -> return getTrelloStrategy()
            "yandex" -> return getYandexStrategy()
        }
        return null
    }


}