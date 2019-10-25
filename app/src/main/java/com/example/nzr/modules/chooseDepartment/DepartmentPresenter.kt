package com.example.nzr.modules.chooseDepartment

import android.util.Log
import com.example.nzr.common.mvp.RXPresenter
import com.example.nzr.data.rest.models.*
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign

class DepartmentPresenter(var view: ChooseDepartmentContract.DepartmentView) :
    ChooseDepartmentContract.DepartmentPresenter, RXPresenter() {

    var trelloRepository = TrelloRepository()
    var yandexRepository = YandexRepository()

    var boards: MutableList<GenericBoardShort> = ArrayList()

    override fun fetchDepartments() {
        subscriptions += yandexRepository.fetchAllQueues()
            .concatMap {
                it.body()?.forEach { task ->
                    boards.add(yandexQueueToGeneric(task))
                    Log.d("fetch", task.id)
                }
                view.updateAdapter(boards)
                trelloRepository.fetchBoards()
            }
            .concatMap {
                it.body()?.forEach { task ->
                    var found = boards.filter { inTask -> inTask.name == task.name }
                    if (found.isNotEmpty()) {
                        Log.d("fetch", found.toString())
                        var index = boards.indexOf(found.first())
                        boards[index].trelloId = task.id
                    } else {
                        boards.add(trelloToGeneric(task))
                    }
                    Log.d("fetch", task.name)
                }
                Observable.just(boards)
            }.subscribe()
    }

}