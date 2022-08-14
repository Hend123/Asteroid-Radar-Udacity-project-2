package com.udacity.asteroidradar.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private fun getInstance():Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(myHttpClient())
           // .addCallAdapterFactory(CoroutineCallAdapterFactory())
                  // .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()
    }

    fun getApiService(): ApiService {
        return getInstance().create(ApiService::class.java)
    }
    fun myHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
            .addInterceptor(MyInterceptor())
        return builder.build()
    }

}