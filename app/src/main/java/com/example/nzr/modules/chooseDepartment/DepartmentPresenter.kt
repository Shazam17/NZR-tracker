package com.example.nzr.modules.chooseDepartment

import android.util.Log
import com.example.nzr.common.mvp.RXPresenter
import com.example.nzr.data.rest.models.*
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign

class  DepartmentPresenter(var view: ChooseDepartmentContract.DepartmentView) :
    ChooseDepartmentContract.DepartmentPresenter, RXPresenter() {

    var trelloRepository = TrelloRepository()
    var yandexRepository = YandexRepository()

    var boards: MutableList<GenericBoardShort> = ArrayList()

    override fun fetchDepartments() {
        subscriptions += yandexRepository.fetchBoards()
            .concatMap {
               boards.addAll(it)
                view.updateAdapter(boards)
                trelloRepository.fetchBoards()
            }
            .subscribe({
                it.forEach { dep ->
                    var found = boards.filter { inTask -> inTask.name == dep.name }
                    if (found.isNotEmpty()) {
                        var index = boards.indexOf(found.first())
                        boards[index].ids.put("trello",dep.ids.get("trello")!!)
                    } else {
                        boards.add(dep)
                    }
                }
                view.updateAdapter(boards)
                Observable.just(null)
            },{
                Log.d("DepartmentPresenter","error ${it.localizedMessage}")
            })
    }

}