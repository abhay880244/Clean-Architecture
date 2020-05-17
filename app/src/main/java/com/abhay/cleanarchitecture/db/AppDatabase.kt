package com.abhay.cleanarchitecture.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhay.cleanarchitecture.models.Articles

@Database(entities = [Articles::class],version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun articleDao() : ArticleDAO

}