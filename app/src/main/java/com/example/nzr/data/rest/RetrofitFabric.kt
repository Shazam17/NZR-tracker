package com.example.nzr.data.rest

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

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
    fun getYandexInterceptor() : OkHttpClient.Builder{
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
    fun getRetrofit(builder : OkHttpClient.Builder, adress:String) : Retrofit {

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