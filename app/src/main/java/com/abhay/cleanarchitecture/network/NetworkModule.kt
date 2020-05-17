package com.abhay.cleanarchitecture.network

import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@Suppress("unused")
object NetworkModule {

    private const val BASE_URL = "http://newsapi.org"

    @Provides
    @Reusable
    @JvmStatic
    fun provideNewsApiInterface(retrofit: Retrofit) : NewsApiInterface{
        return retrofit.create(NewsApiInterface::class.java)
    }


    @Provides
    @Reusable
    @JvmStatic
    internal fun getRetrofitClientInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}