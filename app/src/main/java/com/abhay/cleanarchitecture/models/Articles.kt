package com.abhay.cleanarchitecture.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Articles (
	var author : String?,
	var title : String?,
	var description : String?,
	var publishedAt : String?,
	var content : String?
) {
	@PrimaryKey(autoGenerate = true)
	var id : Int = 0
}