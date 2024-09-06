package com.example.newsapp.Hilt

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.Room.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun porvideRoom(@ApplicationContext context: Context):ArticleDatabase{
        return Room.databaseBuilder(context,ArticleDatabase::class.java,"ArticleDB").build()
    }
}