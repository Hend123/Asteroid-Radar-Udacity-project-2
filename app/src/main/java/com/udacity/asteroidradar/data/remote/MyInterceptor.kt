package com.udacity.asteroidradar.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor  : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val response = chain.proceed(request)
        Log.d("test response",   "hi")
        response.peekBody(2048).let { Log.d("test response",   it.string()) }

        return response
    }
}