package com.example.nzr.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.nzr.R

import com.example.nzr.data.rest.models.listsCards
import kotlinx.android.synthetic.main.kanban_pager_item.view.*




class KanbanPagerAdapter(var lists: List<listsCards>,var context: Context) : RecyclerView.Adapter<KanbanPagerAdapter.PagerHolder>(){

    var current = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHolder {
       return PagerHolder(LayoutInflater
           .from(context)
           .inflate(R.layout.kanban_pager_item,parent,false),context)
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
        var title = view.title
        lateinit var adapter :CardListAdapter

        fun setUp(listsCards: listsCards,pos:Int){
            adapter = CardListAdapter(listsCards.cards,context = context)
            list.adapter = adapter
            list.layoutManager = LinearLayoutManager(context)
            when(pos){
                0 -> title.text = "trello = ${listsCards.name} yandex = Открыто"
                1 -> title.text = "trello = ${listsCards.name} yandex = Требуется информация"
                2 -> title.text = "trello = ${listsCards.name} yandex = В работе"
                3 -> title.text = "trello = ${listsCards.name} yandex = Закрыто"
                else -> title.text = "${listsCards.name}"
            }

        }
    }

}