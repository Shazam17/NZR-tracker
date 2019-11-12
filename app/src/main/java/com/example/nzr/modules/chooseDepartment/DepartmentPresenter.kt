package com.example.nzr.modules.chooseDepartment

import android.util.Log
import com.example.nzr.common.mvp.RXPresenter
import com.example.nzr.data.rest.models.*
import io.reactivex.rxkotlin.plusAssign

class  DepartmentPresenter(var view: ChooseDepartmentContract.DepartmentView) :
    ChooseDepartmentContract.DepartmentPresenter, RXPresenter() {

    lateinit var departmentStategies : List<IDepartmentStrategy>
    var boards: MutableList<GenericBoardShort> = ArrayList()

    override fun fetchDepartments() {

        var strategyFabric = DepartmentStrategyFabric()

        departmentStategies = strategyFabric.getAllStrategies()

        departmentStategies.forEach{ strategy ->
            subscriptions += strategy
                .fetchBoards()
                .subscribe({
                   it.forEach { dep ->
                       var found = boards.filter { inTask -> inTask.name == dep.name }
                       if (found.isNotEmpty()) {
                           var index = boards.indexOf(found.first())
                           boards[index].ids.putAll(dep.ids)
                       } else {
                           boards.add(dep)
                       }
                   }
                    view.updateAdapter(boards)

                },{
                    Log.d("DepartmentPresenter","error ${it.localizedMessage}")
                })
        }
    }

}