package com.example.nzr.modules.chooseDepartment

import com.example.nzr.data.rest.models.GenericBoardShort

interface ChooseDepartmentContract{

    interface DepartmentView{
        fun initAdapter(list:MutableList<GenericBoardShort>)
        fun updateAdapter(depList:MutableList<GenericBoardShort>)
    }

    interface  DepartmentPresenter{
        fun fetchDepartments()
    }

}