package com.example.nzr.modules.CardDetailActivity

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.repository.TrelloRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_card_detail.*

class   CardDetailActivity: AppCompatActivity(), CardDetailContract.CardDetailView,IView{

    var id : String? = ""
    var vendor : Boolean? = null
    var presenter = CardDetailPresenter(this)

    override fun getVendor(): Boolean {
        return vendor!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        id = intent.extras!!.getString("id")
        vendor = intent.extras!!.getBoolean("vendor")
        Log.d("detail",id!!)
        if(!vendor!!){
            presenter.fetchCardByIdYandex(id!!)
        }else{
            presenter.fetchCardByIdTrello(id!!)
        }

        moveToClosed.setOnClickListener {
            presenter.moveToClosed(id!!,"resolved")
        }

        moveTo.setOnClickListener {
            var idIn = buttonGroup.checkedRadioButtonId
            var type:String = ""
            when(idIn){
                R.id.needInfo -> type = "needInfo"
                R.id.inProgress -> type = "inProgress"
                else -> "null"
            }
            presenter.moveToClosed(id!!,type)
        }
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun back() {
        finish()
    }

    override fun initViews(name : String, desc : String){
        nameDetail.text = name
        descDetail.text = desc
    }

}