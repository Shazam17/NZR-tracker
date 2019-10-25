package com.example.nzr.modules.addCard

import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import kotlinx.android.synthetic.main.activity_create_card.*

class AddCardActivity  :AppCompatActivity(), CreateCardContract.AddCardView{


    var presenter  : CreateCardPresenter? = CreateCardPresenter(this)
    var trelloId :String? = null
    var yandexId :String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        trelloId = intent.extras?.getString("trelloId")
        yandexId = intent.extras?.getString("yandexId")

        buttonCreateCard.setOnClickListener{
            var vendorId = vendors.checkedRadioButtonId
            var vendor : Boolean? = null

            if(vendorId == R.id.trelloVendor){
                vendor = true
                presenter?.createCard(cardName.text.toString(),trelloId!!,vendor)
            }else{
                vendor = false
                presenter?.createCard(cardName.text.toString(),yandexId!!,vendor)
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