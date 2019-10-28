package com.example.nzr.modules.addCard

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import kotlinx.android.synthetic.main.activity_create_card.*

class AddCardActivity : AppCompatActivity(), CreateCardContract.AddCardView {


    var presenter: CreateCardPresenter? = CreateCardPresenter(this)
    var trelloId: String? = null
    var yandexId: String? = null

    override fun getActivity(): Activity {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        trelloId = intent.extras?.getString("trelloListId")
        yandexId = intent.extras?.getString("yandexId")

        if(yandexId != "no" ){
            val yandexButton = RadioButton(this)
            yandexButton.text = "yandex"
            yandexButton.id = 0
            vendors.addView(yandexButton)
        }
        if(trelloId != "no" ){
            val trelloButton = RadioButton(this)
            trelloButton.text = "trello"
            trelloButton.id = 1
            vendors.addView(trelloButton)
        }

        buttonCreateCard.setOnClickListener {
            var vendorId = vendors.checkedRadioButtonId
            var vendor: Boolean?

            if (vendorId == 1) {
                vendor = true
                presenter?.createCard(cardName.text.toString(), trelloId!!, vendor)
            } else if(vendorId == 0){
                vendor = false
                presenter?.createCard(cardName.text.toString(), yandexId!!, vendor)
            }

        }

    }

    override fun back() {
        finish()
    }

    override fun getCardId(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}