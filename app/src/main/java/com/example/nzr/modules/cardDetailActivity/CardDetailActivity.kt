package com.example.nzr.modules.cardDetailActivity

import android.app.Activity
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.common.mvp.IView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_card_detail.*
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository


class CardDetailActivity: AppCompatActivity(), CardDetailContract.CardDetailView,IView{

    var card :GenericCardShort? = null
    lateinit var presenter : CardDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.nzr.R.layout.activity_card_detail)

        card = Gson().fromJson(intent.extras!!.getString("card"),GenericCardShort::class.java)
        var pair :Pair<String,String>? = card?.id
        presenter = CardDetailPresenter(this,pair!!)
        presenter.fetch()

        moveTo.setOnClickListener {
            var idIn = buttonGroup.checkedRadioButtonId
            presenter.move(idIn)
        }



    }

    override fun addRadioButton(button: RadioButton) {
        buttonGroup.addView(button)
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun back() {
        finish()
    }

    override fun initViews() {

    }
    override fun initViews(name : String, desc : String){
        nameDetail.text = name
        descDetail.text = desc
    }

}