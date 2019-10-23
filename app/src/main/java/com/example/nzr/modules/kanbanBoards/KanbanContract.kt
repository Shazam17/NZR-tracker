package com.example.nzr.modules.kanbanBoards

import android.app.Activity
import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.models.listsCards
import com.example.nzr.modules.startScreen.StartActivity

interface KanbanContract{

    interface KanbanPresenter{
        fun fetchListsRepTrello()
        fun fetchListsRepYandex()
        fun fetch()
        fun updateList()
    }

    interface KanbanView : IView{
        override fun getActivity(): Activity
        fun initPagerAdapter(lists: List<listsCards>)
        fun getTrelloBoardId():String?
        fun getYandexBoardId():String?
    }

    interface KanbanModel{

    }
}