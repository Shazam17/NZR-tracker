package com.example.nzr.modules.cardDetailActivity

import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.example.nzr.common.mvp.RXPresenter
import io.reactivex.rxkotlin.plusAssign


class CardDetailPresenter(
    var view: CardDetailContract.CardDetailView,
    var pair: Pair<String, String>
) : CardDetailContract.CardDetailPresenter, RXPresenter() {

    var strategy: ICardDetailStrategy
    var rbList = ArrayList<String>()

    init {
        var fabric = CardDetailStrategiesFabric()
        strategy = fabric.getStrategy(pair.first)!!
    }

    override fun fetch() {
        subscriptions += strategy.fetchCard(pair.second)
            .subscribe({
                view.initViews(it.name, "desc")
            }, {
                Log.d("fetchCardDetail", it.localizedMessage!!)
            })
        subscriptions += strategy.fetchTransitions(pair.second)
            .subscribe({
                it.forEachIndexed { index, transition ->
                    var button = RadioButton(view.getActivity())
                    button.id = index
                    button.text = transition.text
                    rbList.add(transition.id!!)
                    view.addRadioButton(button)
                }
            }, {
                Log.d("fetchCardDetail", it.localizedMessage!!)
            })

    }

    override fun move(index: Int) {
        subscriptions += strategy
            .move(pair.second, rbList[index])
            .subscribe({
                Toast
                    .makeText(view.getActivity(), "success moving", Toast.LENGTH_SHORT)
                    .show()
                view.back()
            }, {
                Toast
                    .makeText(
                        view.getActivity(),
                        "Error in moving Card ${it.localizedMessage!!}",
                        Toast.LENGTH_SHORT
                    ).show()
            })
    }
}
