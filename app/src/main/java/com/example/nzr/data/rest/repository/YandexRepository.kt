package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class YandexRepository {

    var repository = RetrofitFabric().getYandex()

    fun fetchAllQueues():Observable<Response<List<QueueShort>>>{
        return repository
            .getAllQueues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchAllBoards(): Observable<Response<List<YandexBoard>>> {
        return repository
            .getAllBoards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCardById(id:String) : Observable<Response<YandexCard>>{
        return repository
            .getCardById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun  fetchCards(filterYandex: Map<String,String>) : Observable<Response<List<YandexCard>>>{
        return repository
            .getCards(filterYandex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun moveCard(id:String , type:String): Observable<Response<List<Transition>>>{
        return repository
            .moveCard(id,type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchTransitionsById(id:String): Observable<Response<List<Transition>>>{
        return repository
            .getTransitions(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun createCard(name:String ,id:String) : Observable<Response<YandexCard>>{

        var map = RequestCreateCardYandexBody(QueueCreate(id),name)
        return repository
            .createCard(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}