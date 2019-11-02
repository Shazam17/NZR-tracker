package com.example.nzr.common.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nzr.R
import com.example.nzr.data.rest.models.CardShort
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.modules.cardDetailActivity.CardDetailActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.card_kanban.view.*

class CardListAdapter(list:ArrayList<GenericCardShort>, val context :Context) :RecyclerView.Adapter<CardListAdapter.CardHolder>(){

    var cardList:ArrayList<GenericCardShort> = list

    class CardHolder(val view: View,val context: Context) : RecyclerView.ViewHolder(view){
        var textField : TextView = view.textCardKanban
        var vendorField : TextView = view.vendorText
        var card :GenericCardShort? = null
        init{
            view.setOnClickListener{
                var intent = Intent(context,CardDetailActivity::class.java)
                intent.putExtra("card", Gson().toJson(card))
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.textField.text = cardList.get(position).name
        holder.card = cardList[position]
        holder.vendorField.text = "vendor = ${cardList[position].id.first}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
            return CardHolder(LayoutInflater.from(context)
                .inflate(R.layout.card_kanban,parent,false),context)
    }
}