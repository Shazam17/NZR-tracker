package com.example.nzr.modules.kanbanBoards

import android.app.Activity
import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.data.rest.models.ListsCards

interface KanbanContract{

    interface KanbanPresenter{
        fun fetch(ids:MutableMap<String,String>)
        fun updateList()
        fun getTrelloListId(position:Int):String
    }

    interface KanbanView : IView{
        override fun getActivity(): Activity
        fun initPagerAdapter(lists: ArrayList<ArrayList<GenericCardShort>>)
        fun setRefresh(refresh:Boolean)
    }

}