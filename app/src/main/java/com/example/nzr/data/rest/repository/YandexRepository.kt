package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class YandexRepository :IRepository{

    var repository = RetrofitFabric().getYandex()

    override fun fetchCardById(id:String) : Observable<GenericCardDetail>{
        return repository
            .getCardById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                Observable.just(cardToGenericDetail(it.body()!!,"yandex"))
            }
    }
    override fun createCard(name:String ,id:String) : Observable<GenericCardDetail>{

        var map = RequestCreateCardYandexBody(QueueCreate(id),name)
        return repository
            .createCard(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                Observable.just(cardToGenericDetail(it.body()!!,"yandex"))
            }
    }

    override fun fetchBoards(): Observable<ArrayList<GenericBoardShort>>{
        return repository
            .getAllQueues()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                var list = ArrayList<GenericBoardShort>()
                it.body()!!.forEach {
                    list.add(yandexQueueToGeneric(it))
                }
                Observable.just(list)
            }
    }
     fun fetchBoards2(): Observable<List<GenericBoardShort>> {
        return repository
            .getAllBoards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                var list = ArrayList<GenericBoardShort>()
                it.body()!!.forEach {
                    list.add(yandexToGeneric(it))
                }
                Observable.just(list)
            }
    }

    override fun fetchCardsById(id: String): Observable<ArrayList<ArrayList<GenericCardShort>>> {
        return repository
            .getCards(mapOf("queue" to id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {
                var lists = ArrayList<ArrayList<GenericCardShort>>()
                for(i in 0..4){
                    lists.add(ArrayList())
                }
                var lsOpen : ArrayList<GenericCardShort> = ArrayList()
                it.body()!!.forEach { task -> if(task.status.key.equals("open")) lsOpen.add(GenericCardShort(Pair("yandex",task.id),task.summary))}
                lists[0].addAll(lsOpen)

                var lsNeedInformation : ArrayList<GenericCardShort> = ArrayList()
                it.body()!!.forEach { task -> if(task.status.key.equals("needInfo")) lsNeedInformation.add(GenericCardShort(Pair("yandex",task.id),task.summary))}
                lists[1].addAll(lsNeedInformation)

                var lsInWork : ArrayList<GenericCardShort> = ArrayList()
                it.body()!!.forEach { task -> if(task.status.key.equals("inProgress")) lsInWork.add(GenericCardShort(Pair("yandex",task.id),task.summary))}
                lists[2].addAll(lsInWork)

                var lsClosed : ArrayList<GenericCardShort> = ArrayList()
                it.body()!!.forEach { task -> if(task.status.key.equals("resolved")) lsClosed.add(GenericCardShort(Pair("yandex",task.id),task.summary))}
                lists[3].addAll(lsClosed)
                Observable.just(lists)
            }
    }



    fun moveCard(id:String , type:String): Observable<Response<List<Transition>>>{
        return repository
            .moveCard(id,type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun fetchTranistions(id: String): Observable<ArrayList<GenericTransition>> {
        return repository
            .getTransitions(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap{
                var list = ArrayList<GenericTransition>()
                it.body()!!.forEach { transition ->
                    list.add(GenericTransition(transition.to.key,transition.to.key))
                }
                Observable.just(list)
            }
    }



}