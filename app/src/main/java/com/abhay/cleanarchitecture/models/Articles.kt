package com.abhay.cleanarchitecture.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Articles (

	val source : Source,
	val author : String,
	val title : String,
	val description : String,
	val url : String,
	val urlToImage : String,
	val publishedAt : String,
	val content : String
) {
	@PrimaryKey(autoGenerate = true)
	val id : Int = 0
}