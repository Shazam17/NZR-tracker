package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class TrelloRepository :IRepository {

    var trelloFabric = RetrofitFabric().getTrello()

    override fun fetchCardById(id :String) : Observable<GenericCardDetail> {
        var fields = "name,desc"
        return trelloFabric
            .getCardById(id, fields)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                Observable.just(cardToGenericDetail(it.body()!!,"trello"))
            }
    }
    override fun createCard(idList: String,name:String) : Observable<GenericCardDetail>{
        var map = mapOf("idList" to idList, "name" to name)
        return trelloFabric
            .createCard(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                Observable.just(cardToGenericDetail(it.body()!!,"trello"))
            }
    }

    override fun fetchCardsById(id:String): Observable<ArrayList<ArrayList<GenericCardShort>>>{
        var map = mapOf("cards" to "all",
            "card_fields" to "name",
            "filter" to "open",
            "fields" to "name")
        return trelloFabric
            .getListsOfBoard(id,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                var lists = ArrayList<ArrayList<GenericCardShort>>()
                it.body()!!.forEach {
                    lists.add(ArrayList())
                }
                it.body()!!.forEachIndexed { index, listsCards ->
                    listsCards.cards.forEach {
                        lists[index].add(cardToGenericShort(it,"trello"))
                    }
                }
                Observable.just(lists)
            }
    }

    override fun fetchBoards():Observable<ArrayList<GenericBoardShort>>{
        var map = mapOf("fields" to "all")

        return trelloFabric
            .getAllBoards(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                var list = ArrayList<GenericBoardShort>()
                it.body()!!.forEach{
                    list.add(trelloToGeneric(it))
                }

                Observable.just(list)
            }
    }



    fun updateCard(idCard:String ,idList:String):Observable<Response<CardDetail>>{
        var map = mapOf("idList" to idList)
        return trelloFabric
            .updateCard(idCard , map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchBoardIdOfCard(id:String) : Observable<Response<Board>>{
        return trelloFabric
            .getBoardIdOfCard(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }



    fun createList(idBoard:String , name:String):Observable<Response<Board>>{
        var map = mapOf("idBoard" to idBoard, "name" to name)
        return trelloFabric
            .createList(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}