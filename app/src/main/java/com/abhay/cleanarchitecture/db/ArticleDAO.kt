package com.abhay.cleanarchitecture.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abhay.cleanarchitecture.models.Articles

@Dao
interface ArticleDAO {

    @get : Query("select * from articles")
    val all : List<Articles>

    @Insert
    fun insertAll(vararg articles: Articles)
}