package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.Board
import com.example.nzr.data.rest.models.CardDetail
import com.example.nzr.data.rest.models.ListsCards
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class TrelloRepository {

    var trelloFabric =  RetrofitFabric().getTrello()

    fun fetchCardsById(id:String):Observable<Response<List<ListsCards>>>{
        var map = mapOf("cards" to "all",
            "card_fields" to "name",
            "filter" to "open",
            "fields" to "name")
        return trelloFabric
            .getListsOfBoard(id,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchBoards(): Observable<Response<List<Board>>> {
        var map = mapOf("fields" to "all")

        return trelloFabric
            .getAllBoards(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCardById(id :String) :Observable<Response<CardDetail>> {
        var fields = "name,desc"
        return trelloFabric
            .getCardById(id, fields)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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

    fun createCard(idList: String,name:String):Observable<Response<CardDetail>>{
        var map = mapOf("idList" to idList, "name" to name)
        return trelloFabric
            .createCard(map)
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