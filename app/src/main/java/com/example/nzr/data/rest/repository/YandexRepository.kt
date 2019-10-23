package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.YandexRequests
import com.example.nzr.data.rest.models.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class YandexRepository {

    var repository = RetrofitFabric().getYandex()

    fun fetchAllQueues():Observable<Response<List<queueShort>>>{
        return repository
            .getAllQueues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchAllBoards(): Observable<Response<List<yandexBoard>>> {
        return repository
            .getAllBoards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchCardById(id:String) : Observable<Response<yandexCard>>{
        return repository
            .getCardById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun  fetchCards(filterYandex: Map<String,String>) : Observable<Response<List<yandexCard>>>{
        return repository
            .getCards(filterYandex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun moveCard(id:String , type:String): Observable<Response<List<transition>>>{
        return repository
            .moveCard(id,type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
//
//    fun createCard(name:String ,id:String) : Observable<Response<yandexCard>>{
//        //var map = requestCreateCardYandexBody(queueShort(id , name))
//        return repository
//            .createCard(map)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    }


}