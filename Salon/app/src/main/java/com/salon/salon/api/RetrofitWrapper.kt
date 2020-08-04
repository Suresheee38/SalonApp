package com.salon.salon.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitWrapper {
    companion object {
        private const val BASE_URL = "http://ec2-13-127-128-156.ap-south-1.compute.amazonaws.com/v1/"
        private val httpClient  = OkHttpClient.Builder().build()
        private val retrofitBuilder = Retrofit.Builder().baseUrl(BASE_URL).client(httpClient).addConverterFactory(GsonConverterFactory.create())
        private val retrofit = retrofitBuilder.build()
    }
    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}