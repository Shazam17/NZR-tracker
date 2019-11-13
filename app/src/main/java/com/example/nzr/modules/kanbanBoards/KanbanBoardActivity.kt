package com.example.nzr.modules.kanbanBoards

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activty_kanban.*
import android.view.Menu
import android.content.Intent
import android.view.MenuItem
import com.example.nzr.data.rest.models.GenericBoardShort
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.modules.addCard.AddCardActivity
import com.google.gson.Gson


class KanbanBoardActivity :AppCompatActivity() ,KanbanContract.KanbanView{


    private var presenter =  KanbanPresenter(this)

    lateinit var adapter : KanbanPagerAdapter
    lateinit var boardShort: GenericBoardShort

    val BOARD = "board"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.nzr.R.layout.activty_kanban)
        boardShort = Gson().fromJson(intent.extras!!.getString(BOARD), GenericBoardShort::class.java)

        title = "Доска ${boardShort.name}"
        presenter.fetch(boardShort.ids)
        initViews()
    }

    override fun initViews() {
        swipeToRefreshKanban.setOnRefreshListener {
            presenter.fetch(boardShort.ids)
        }
    }

    override fun setRefresh(refresh:Boolean){
        swipeToRefreshKanban.setRefreshing(refresh)
    }

    override fun onResume() {
        super.onResume()
        presenter.fetch(boardShort.ids)
    }

    override fun initPagerAdapter(lists: ArrayList<ArrayList<GenericCardShort>>){
        adapter = KanbanPagerAdapter(lists, this)
        adapter.notifyDataSetChanged()
        pager.adapter = adapter
    }


    override fun getActivity() : Activity{
        return this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.example.nzr.R.menu.menu_board, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            com.example.nzr.R.id.add  ->{
                if(presenter.lists.isNotEmpty()){
                    var intent = Intent(this,AddCardActivity::class.java)
                    intent.putExtra("board",Gson().toJson(boardShort))
                    startActivity(intent)
                }
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }
}