package com.example.nzr.modules.chooseDepartment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nzr.R
import com.example.nzr.data.rest.models.GenericBoardShort
import com.example.nzr.modules.kanbanBoards.KanbanBoardActivity
import kotlinx.android.synthetic.main.card_kanban.view.*

class DepartmentAdapter(var departs :MutableList<GenericBoardShort>, val context: Context) :RecyclerView.Adapter<DepartmentAdapter.DepartHolder>(){


    class DepartHolder(var view : View,val context: Context) : RecyclerView.ViewHolder(view){
        val name = view.textCardKanban
        var card = view.card
        var vendors = view.vendorText
        var trelloId :String? = null
        var yandexId :String? = null
        init{
                view.setOnClickListener{
                    var intent = Intent(context,KanbanBoardActivity::class.java)
                    intent.putExtra("trello",trelloId)
                    intent.putExtra("yandex",yandexId)
                    intent.putExtra("name",name.text.toString())

                    context.startActivity(intent)
                }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartHolder {
        return DepartHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.card_kanban, parent, false), context
        )
    }

    override fun getItemCount(): Int {
        return departs.size
    }

    override fun onBindViewHolder(holder: DepartHolder, position: Int) {
        holder.name.text = departs.get(position).name
        holder.trelloId = departs.get(position).trelloId
        holder.yandexId = departs.get(position).yandexId
        if(holder.trelloId != null){
            holder.vendors.text = "trello"
        }
        if(holder.yandexId != null){
            holder.vendors.text = "yandex"
        }
        if(holder.trelloId != null && holder.yandexId != null ){
            holder.vendors.text = "yandex/trello"

        }

    }
}