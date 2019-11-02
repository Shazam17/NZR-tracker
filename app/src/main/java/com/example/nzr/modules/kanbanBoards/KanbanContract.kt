package com.example.nzr.modules.kanbanBoards

import android.app.Activity
import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.data.rest.models.ListsCards

interface KanbanContract{

    interface KanbanPresenter{
        fun fetch(ids:MutableMap<String,String>)

    }

    interface KanbanView : IView{
        fun initPagerAdapter(lists: ArrayList<ArrayList<GenericCardShort>>)
        fun setRefresh(refresh:Boolean)
    }

}