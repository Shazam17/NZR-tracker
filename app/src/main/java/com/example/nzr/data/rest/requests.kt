package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.board
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TrelloRequests{


    @GET("boards/{boardID}")
    fun getBoard(@Path("boardID")boardId:String) :Observable<board>

}