package com.abhay.cleanarchitecture.network

import com.abhay.cleanarchitecture.models.NewsResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface NewsApiInterface {

    @GET("/v2/top-headlines?country=us&category=business&apiKey=bf4aaf430a79426e8b87036f64de97ac")
    fun getNews() : Observable<NewsResponse>
}