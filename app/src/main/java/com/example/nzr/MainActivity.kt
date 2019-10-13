package com.example.nzr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.nzr.data.rest.TrelloRequests

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


//936bbab43463e479a095c368eb847f35


// trelo server token dbaca998bd52ec777318a316442f4997c9441537b97f22e5fb9663288b5aa56d

// oAuth1 trello e04dff0b9d02b6259e5aa1e61731c3a250448539c0d44aa5dbd172854ad2569d


class NZRInterceptor :Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()

        var requestBuiler = original.newBuilder()
            .addHeader("key","936bbab43463e479a095c368eb847f35")
            .addHeader("token","dbaca998bd52ec777318a316442f4997c9441537b97f22e5fb9663288b5aa56d")
        var req = requestBuiler.build()
        return chain.proceed(req)
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var okHttpClientBuilder = okhttp3.OkHttpClient.Builder()


        okHttpClientBuilder.addInterceptor(NZRInterceptor())

        var http3Client = okHttpClientBuilder.build()


        var retrofit: Retrofit =  Retrofit.Builder()
            .baseUrl("https://api.trello.com/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //.client(http3Client)
            .build()
        
        var trello : TrelloRequests =  retrofit.create(TrelloRequests::class.java)

        var boardObser =  trello.getBoard("30uyYyEU").subscribe(
            {
                Log.d("main","successs")
                Log.d("main",it.name)

            },{
                Log.d("main","error")
                Log.d("main",it.localizedMessage)

            })

    }



}
