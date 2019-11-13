package com.example.nzr.modules.chooseDepartment

import com.example.nzr.data.rest.IStrategyFabric
import com.example.nzr.data.rest.models.GenericBoardShort
import com.example.nzr.data.rest.repository.IRepository
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable

interface IDepartmentStrategy{
    fun fetchBoards() : Observable<ArrayList<GenericBoardShort>>
}

class GenericDepartmentStrategy<Type:IRepository>(var repository: Type) : IDepartmentStrategy{
    override fun fetchBoards(): Observable<ArrayList<GenericBoardShort>> {
        return repository.fetchBoards()
    }
}

class DepartmentStrategyFabric : IStrategyFabric<IDepartmentStrategy> {


    override fun getYandexStrategy() : IDepartmentStrategy{
        return GenericDepartmentStrategy(YandexRepository())
    }

    override fun getTrelloStrategy() : IDepartmentStrategy{
        return GenericDepartmentStrategy(TrelloRepository())
    }

    fun getAllStrategies() : ArrayList<IDepartmentStrategy>{
        var list = ArrayList<IDepartmentStrategy>()

        list.add(getYandexStrategy())
        list.add(getTrelloStrategy())

        return list
    }
}
