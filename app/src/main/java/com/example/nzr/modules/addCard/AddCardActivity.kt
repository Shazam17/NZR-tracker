package com.example.nzr.modules.addCard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import kotlinx.android.synthetic.main.activity_create_card.*

class AddCardActivity  :AppCompatActivity(), CreateCardContract.AddCardView{


    var presenter  : CreateCardPresenter? = CreateCardPresenter(this)
    var vendor:Boolean? = null
    var id :String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        vendor = intent.extras?.getBoolean("vendor")
        id = intent.extras?.getString("boardId")

        buttonCreateCard.setOnClickListener{
            presenter?.createCard(cardName.text.toString(),id!!,vendor!!)
        }

    }

    override fun back() {
        finish()
    }

    override fun getCardId(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}