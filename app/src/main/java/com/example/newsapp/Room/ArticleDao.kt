package com.example.newsapp.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsapp.module.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Upsert()
    suspend fun upsertArticle(article: Article)
    @Query("SELECT * FROM Article")
     fun allArticle():Flow<List<Article>>
     @Delete
     suspend fun delete(article: Article)
}