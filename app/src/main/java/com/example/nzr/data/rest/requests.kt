package com.example.nzr.data.rest

import com.example.nzr.data.rest.models.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.Interceptor
import retrofit2.http.*
import java.io.IOException


interface TrelloRequests{


    @GET("boards/{boardID}")
    fun getBoard(@Path("boardID")boardId:String) :Observable<Response<board>>

    @GET("boards/{boardID}/lists")
    fun getListsOfBoard(@Path("boardID")boardId:String,@QueryMap params: Map<String, String>) :Observable<Response<List<listsCards>>>

    @GET("cards/{cardId}")
    fun getCardById(@Path("cardId")cardId:String, @Query("fields")fields:String) : Observable<Response<cardDetail>>

    @GET("members/5992868b5f6b925617fc350c/boards")
    fun getAllBoards(@QueryMap params:Map<String,String>):Observable<Response<List<board>>>

    @POST("cards/")
    fun createCard(@QueryMap params:Map<String,String>)

    @PUT("cards/")
    fun updateCard(@QueryMap params:Map<String,String>)

    @DELETE("cards/")
    fun deleteCard(@Query("id") id:String)


}
interface YandexRequests{

    @GET("boards")
    fun getAllBoards():Observable<Response<List<yandexBoard>>>

    @GET("queues")
    fun getAllQueues():Observable<Response<List<queueShort>>>

    @GET("issues/{cardId}")
    fun getCardById(@Path("cardId") cardId:String) :Observable<Response<yandexCard>>

    @POST("issues/_search")
    fun getCards(@Body filter: Map<String,String>) : Observable<Response<List<yandexCard>>>


    @POST("issues/")
    fun createCard(@Body params:requestCreateCardYandexBody) :Observable<Response<yandexCard>>
}

class RetrofitFabric{
    fun getTrelloInterceptor(): OkHttpClient.Builder{
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", "936bbab43463e479a095c368eb847f35")
                    .addQueryParameter("token","dbaca998bd52ec777318a316442f4997c9441537b97f22e5fb9663288b5aa56d")
                    .build()

                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        return httpClient
    }
    fun getYandexInterceptor() :OkHttpClient.Builder{
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                    .build()

                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                    .addHeader("X-Org-Id","3412023")

                    .header("Authorization","OAuth AgAAAAAUjHxjAAXqvJEutRVo7kE9sfwnpdzFs9A")
                    .url(url)

                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        return httpClient
    }
    fun getRetrofit(builder : OkHttpClient.Builder,adress:String) :Retrofit{

        return Retrofit.Builder()
            .baseUrl(adress)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(builder.build())
            .build()
    }


    fun getTrello() : TrelloRequests{
        return getRetrofit(getTrelloInterceptor(),"https://api.trello.com/1/").create(TrelloRequests::class.java)
    }
    fun getYandex() : YandexRequests{
        return getRetrofit(getYandexInterceptor(),"https://api.tracker.yandex.net/v2/").create(YandexRequests::class.java)
    }
}
