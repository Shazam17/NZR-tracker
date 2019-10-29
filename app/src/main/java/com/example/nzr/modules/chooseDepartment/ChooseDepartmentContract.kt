package com.example.nzr.modules.chooseDepartment

import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.models.GenericBoardShort

interface ChooseDepartmentContract{

    interface DepartmentView : IView{
        fun updateAdapter(depList:MutableList<GenericBoardShort>)
    }

    interface  DepartmentPresenter{
        fun fetchDepartments()
    }

}