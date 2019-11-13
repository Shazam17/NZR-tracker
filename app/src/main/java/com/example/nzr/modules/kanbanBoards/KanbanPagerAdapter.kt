package com.example.nzr.modules.kanbanBoards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nzr.R
import com.example.nzr.common.adapters.CardListAdapter
import com.example.nzr.data.rest.models.GenericCardShort

import kotlinx.android.synthetic.main.kanban_pager_item.view.*




class KanbanPagerAdapter(var lists: ArrayList<ArrayList<GenericCardShort>>, var context: Context) : RecyclerView.Adapter<KanbanPagerAdapter.PagerHolder>(){

    var current = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHolder {
       return PagerHolder(
           LayoutInflater
               .from(context)
               .inflate(R.layout.kanban_pager_item, parent, false), context
       )
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun getCurrentIndex():Int{
        return current
    }

    override fun onBindViewHolder(holder: PagerHolder, position: Int) {
        holder.setUp(lists.get(position),position)
        current = position
    }

    class PagerHolder(var view: View,var context: Context) : RecyclerView.ViewHolder(view){

        var list = view.list
        lateinit var adapter : CardListAdapter

        fun setUp(cardList: ArrayList<GenericCardShort>, pos:Int){
            adapter =
                CardListAdapter(cardList, context = context)
            list.adapter = adapter
            list.layoutManager = LinearLayoutManager(context)
        }
    }

}