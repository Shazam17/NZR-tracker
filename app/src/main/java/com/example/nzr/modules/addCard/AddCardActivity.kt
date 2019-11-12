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
    lateinit var map:Map<String,String>

    override fun getActivity(): Activity {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        initViews()
    }

    override fun initViews() {

        var idIn:Int = 0
        map.forEach { vendor, id ->
            val button = RadioButton(this)
            button.text = vendor
            button.id = idIn
            vendors.addView(button)
            idIn++
        }

        buttonCreateCard.setOnClickListener {
            var vendorId = vendors.checkedRadioButtonId
            presenter.createCard(vendorId)
        }
    }
    override fun back() {
        finish()
    }

}