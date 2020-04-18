package com.abhay.cleanarchitecture.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SecureRetrofitClient {
    
    companion object {

        private const val BASE_URL = "http://newsapi.org"

        fun getRetrofitClientInstance() : Retrofit{
            return  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

    }
}