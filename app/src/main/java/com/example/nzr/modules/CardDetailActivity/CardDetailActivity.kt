package com.example.nzr.modules.CardDetailActivity

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.nzr.R
import com.example.nzr.common.mvp.IView
import com.example.nzr.data.rest.RetrofitFabric
import com.example.nzr.data.rest.models.transition
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
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

        var rbList = ArrayList<String>()

        if(!vendor!!){
            var res =YandexRepository()
                .fetchTransitionsById(id!!)
                .subscribe({
                    if(it.isSuccessful){
                        it.body()!!.forEachIndexed {index ,transition ->
                            var button = RadioButton(this)
                            button.id = index
                            button.text = transition.to.key
                            rbList.add(transition.to.key)
                            buttonGroup.addView(button)
                        }
                    }
                },{

                })
        }else{
            var trello = TrelloRepository()
            var res = trello
                .fetchBoardIdOfCard(id!!)
                .concatMap {
                    var boardId = it.body()!!.id
                    trello.fetchCardsById(boardId)
                }.subscribe({
                    it.body()!!.forEachIndexed {index ,list ->
                        var button = RadioButton(this)
                        button.id = index
                        button.text = list.name
                        rbList.add(list.id)
                        buttonGroup.addView(button)
                    }
                },{

                })
        }



        moveTo.setOnClickListener {
            if(vendor!!){
                var idIn = buttonGroup.checkedRadioButtonId
                presenter.moveToClosed(id!!,rbList.get(idIn))
            }else{
                var idIn = buttonGroup.checkedRadioButtonId
                presenter.moveToClosed(id!!,rbList.get(idIn))
            }
        }


        moveToTable.setOnClickListener {
            presenter.move()
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