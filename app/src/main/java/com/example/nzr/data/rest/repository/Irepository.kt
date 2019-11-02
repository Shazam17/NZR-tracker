package com.example.nzr.data.rest.repository

import com.example.nzr.data.rest.models.*
import io.reactivex.Observable
import retrofit2.Response

interface IRepository {
    fun fetchCardById(id:String): Observable<GenericCardDetail>
    fun fetchCardsById(id:String): Observable<ArrayList<ArrayList<GenericCardShort>>>
    fun fetchBoards(): Observable<ArrayList<GenericBoardShort>>
    fun fetchTranistions(id:String): Observable<ArrayList<GenericTransition>>
    fun createCard(name:String ,id:String): Observable<GenericCardDetail>

}