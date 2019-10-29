package com.example.nzr.modules.chooseDepartment

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nzr.R
import com.example.nzr.data.rest.models.GenericBoardShort
import kotlinx.android.synthetic.main.activity_choose_dep.*

class ChooseDepActivity : AppCompatActivity()  ,
    ChooseDepartmentContract.DepartmentView {

    lateinit var presenter: DepartmentPresenter
    lateinit var adapter : DepartmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_dep)
        presenter = DepartmentPresenter(this)
        presenter.fetchDepartments()

        initViews()
    }

    override fun initViews() {

    }

    override fun getActivity(): Activity {
        return this
    }
    override fun updateAdapter(depList:MutableList<GenericBoardShort>){
        adapter = DepartmentAdapter(depList, this)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

    }
}
