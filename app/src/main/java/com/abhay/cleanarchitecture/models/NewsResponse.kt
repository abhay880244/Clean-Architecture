package com.abhay.cleanarchitecture.models

data class NewsResponse (

	val status : String,
	val totalResults : Int,
	val articles : List<Articles>
)