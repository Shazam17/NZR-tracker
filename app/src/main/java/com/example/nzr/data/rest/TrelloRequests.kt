package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*


interface TrelloRequests{

    @GET("boards/{boardID}")
    fun getBoard(@Path("boardID")boardId:String) :Observable<Response<BoardTrello>>

    @GET("boards/{boardID}/lists")
    fun getListsOfBoard(@Path("boardID")boardId:String,@QueryMap params: Map<String, String>) :Observable<Response<List<ListsCardsTrello>>>

    @GET("cards/{cardId}")
    fun getCardById(@Path("cardId")cardId:String, @Query("fields")fields:String) : Observable<Response<CardDetailTrello>>

    @GET("members/5992868b5f6b925617fc350c/boards")
    fun getAllBoards(@QueryMap params:Map<String,String>):Observable<Response<List<BoardTrello>>>

    @GET("cards/{cardId}/board")
    fun getBoardIdOfCard(@Path("cardId")cardId:String) : Observable<Response<BoardTrello>>

    @POST("cards/")
    fun createCard(@QueryMap params:Map<String,String>): Observable<Response<CardDetailTrello>>

    @POST("lists")
    fun createList(@QueryMap params:Map<String,String>):Observable<Response<BoardTrello>>

    @PUT("cards/{cardId}")
    fun updateCard(@Path("cardId")cardId:String,@QueryMap params:Map<String,String>): Observable<Response<CardDetailTrello>>

    @DELETE("cards/")
    fun deleteCard(@Query("id") id:String)

}



