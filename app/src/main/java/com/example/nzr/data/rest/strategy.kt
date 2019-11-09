package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.GenericBoardShort
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import kotlin.collections.ArrayList

interface IDepartmentStrategy{
    fun fetchBoards(): Observable<ArrayList<GenericBoardShort>>
}

interface IStrategyFabric{
    fun getYandexStrategy():IDepartmentStrategy
    fun getTrelloStrategy():IDepartmentStrategy
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
    override fun fetchBoards(): Observable<ArrayList<GenericBoardShort>> {
        var list = ArrayList<GenericBoardShort>()


        return trelloRepository.fetchBoards()
    }
}

class DepartmentStrategyFabric : IStrategyFabric{


    override fun getYandexStrategy():IDepartmentStrategy{
        return DepartmentYandexStrategy()
    }

    override fun getTrelloStrategy():IDepartmentStrategy{
        return DepartmentTrelloStrategy()
    }

    fun getAllDepartments():List<IDepartmentStrategy>{
        var list = ArrayList<IDepartmentStrategy>()

        list.add(getYandexStrategy())
        list.add(getTrelloStrategy())

        return list
    }
}