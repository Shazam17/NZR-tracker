package com.example.nzr.modules.kanbanBoards

import android.app.Activity
import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.models.ListsCards

interface KanbanContract{

    interface KanbanPresenter{
        fun fetchListsRepTrello()
        fun fetchListsRepYandex()
        fun fetch()
        fun updateList()
        fun getTrelloListId(position:Int):String
    }

    interface KanbanView : IView{
        override fun getActivity(): Activity
        fun initPagerAdapter(lists: List<ListsCards>)
        fun getTrelloBoardId():String?
        fun getYandexBoardId():String?
        fun setRefresh(refresh:Boolean)
    }

}