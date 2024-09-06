package com.example.newsapp.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.module.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class ArticleDatabase :RoomDatabase(){
    abstract fun articledao():ArticleDao
}