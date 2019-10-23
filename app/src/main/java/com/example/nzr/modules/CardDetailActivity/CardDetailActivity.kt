package com.example.nzr.modules.CardDetailActivity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.repository.TrelloRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_card_detail.*

class   CardDetailActivity: AppCompatActivity(), CardDetailContract.CardDetailView{

    var id : String? = ""
    var vendor : Boolean? = null
    var presenter = CardDetailPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)
        id = intent.extras!!.getString("id")
        vendor = intent.extras!!.getBoolean("vendor")

        Log.d("detail",vendor.toString())
        if(!vendor!!){
            presenter.fetchCardByIdYandex(id!!)
        }else{
            presenter.fetchCardByIdTrello(id!!)
        }

    }


    override fun initViews(name : String , desc : String){
        nameDetail.text = name
        descDetail.text = desc
    }

}