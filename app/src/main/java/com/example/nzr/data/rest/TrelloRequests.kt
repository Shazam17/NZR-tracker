package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import retrofit2.http.*
import java.io.IOException


interface TrelloRequests{

    @GET("boards/{boardID}")
    fun getBoard(@Path("boardID")boardId:String) :Observable<Response<Board>>

    @GET("boards/{boardID}/lists")
    fun getListsOfBoard(@Path("boardID")boardId:String,@QueryMap params: Map<String, String>) :Observable<Response<List<ListsCards>>>

    @GET("cards/{cardId}")
    fun getCardById(@Path("cardId")cardId:String, @Query("fields")fields:String) : Observable<Response<CardDetail>>

    @GET("members/5992868b5f6b925617fc350c/boards")
    fun getAllBoards(@QueryMap params:Map<String,String>):Observable<Response<List<Board>>>

    @GET("cards/{cardId}/board")
    fun getBoardIdOfCard(@Path("cardId")cardId:String) : Observable<Response<Board>>

    @POST("cards/")
    fun createCard(@QueryMap params:Map<String,String>): Observable<Response<CardDetail>>

    @POST("lists")
    fun createList(@QueryMap params:Map<String,String>):Observable<Response<Board>>

    @PUT("cards/{cardId}")
    fun updateCard(@Path("cardId")cardId:String,@QueryMap params:Map<String,String>): Observable<Response<CardDetail>>

    @DELETE("cards/")
    fun deleteCard(@Query("id") id:String)

}



