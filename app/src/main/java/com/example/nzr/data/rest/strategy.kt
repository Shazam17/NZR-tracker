package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.GenericBoardShort
import com.example.nzr.data.rest.models.GenericCardDetail
import com.example.nzr.data.rest.models.GenericCardShort
import com.example.nzr.data.rest.repository.TrelloRepository
import com.example.nzr.data.rest.repository.YandexRepository
import io.reactivex.Observable
import kotlin.collections.ArrayList


interface IStrategyFabric<Type>{
    fun getYandexStrategy():Type
    fun getTrelloStrategy():Type
}









