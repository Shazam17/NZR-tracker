package com.example.nzr.modules.startScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nzr.R
import com.example.nzr.common.adapters.DepartmentAdapter
import com.example.nzr.data.rest.models.board
import com.example.nzr.data.rest.models.genericBoardShort
import kotlinx.android.synthetic.main.activity_choose_dep.*

class ChooseDepActivity : AppCompatActivity()  , DepartmentContract.DepartmentView{

    lateinit var presenter: DepartmentPresenter
    lateinit var adapter :DepartmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_dep)
        presenter = DepartmentPresenter(this)
        presenter.fetchDepartments()




        //TODO вывести список отделов
        //TODO навигация от отдела к доскам
    }

    override fun initAdapter(depList:MutableList<genericBoardShort>) {

    }
    override fun updateAdapter(depList:MutableList<genericBoardShort>){
        adapter =  DepartmentAdapter(depList,this)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

    }
}
