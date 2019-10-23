package com.example.nzr.common.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nzr.R
import com.example.nzr.data.rest.models.cardShort
import com.example.nzr.modules.CardDetailActivity.CardDetailActivity
import kotlinx.android.synthetic.main.card_kanban.view.*

class CardListAdapter(list:List<cardShort>,val context :Context) :RecyclerView.Adapter<CardListAdapter.CardHolder>(){

    var cardList:List<cardShort> = list

    class CardHolder(val view: View,val context: Context) : RecyclerView.ViewHolder(view){
        var text : TextView = view.textCardKanban
        var vendor : Boolean? = null
        var id :String = ""
        init{
            view.setOnClickListener{
                var intent = Intent(context,CardDetailActivity::class.java)
                Log.d("detail",vendor.toString())
                intent.putExtra("id",id)
                intent.putExtra("vendor",vendor)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.text.text = cardList.get(position).name
        holder.id = cardList.get(position).id
        holder.vendor = cardList.get(position).vendor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
            return CardHolder(LayoutInflater.from(context)
                .inflate(R.layout.card_kanban,parent,false),context)
    }
}