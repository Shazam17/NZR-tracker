package com.example.nzr.modules.chooseDepartment

import com.example.nzr.data.rest.IStrategyFabric
import com.example.nzr.data.rest.models.GenericBoardShort
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable

interface IDepartmentStrategy{
    fun fetchBoards() : Observable<ArrayList<GenericBoardShort>>
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

class DepartmentStrategyFabric : IStrategyFabric<IDepartmentStrategy> {


    override fun getYandexStrategy() : IDepartmentStrategy{
        return DepartmentYandexStrategy()
    }

    override fun getTrelloStrategy() : IDepartmentStrategy{
        return DepartmentTrelloStrategy()
    }

    fun getAllStrategies() : ArrayList<IDepartmentStrategy>{
        var list = ArrayList<IDepartmentStrategy>()

        list.add(getYandexStrategy())
        list.add(getTrelloStrategy())

        return list
    }
}
