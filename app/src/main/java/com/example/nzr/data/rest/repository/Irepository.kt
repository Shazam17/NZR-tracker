package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.models.CardDetail
import com.example.nzr.data.rest.models.GenericBoardShort
import com.example.nzr.data.rest.models.GenericCardDetail
import com.example.nzr.data.rest.models.GenericCardShort
import io.reactivex.Observable
import retrofit2.Response

interface IRepository {
    fun fetchCardById(id:String): Observable<GenericCardDetail>
    fun createCard(name:String ,id:String): Observable<GenericCardDetail>
    fun fetchCardsById(id:String) : Observable<ArrayList<ArrayList<GenericCardShort>>>
    fun fetchBoards():Observable<ArrayList<GenericBoardShort>>

}