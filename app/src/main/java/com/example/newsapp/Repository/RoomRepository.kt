package com.example.newsapp.Repository

import androidx.compose.runtime.collectAsState
import androidx.room.RoomDatabase
import com.example.newsapp.Room.ArticleDatabase
import com.example.newsapp.module.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RoomRepository @Inject constructor(private val database: ArticleDatabase) {
    private val _allarticle= MutableStateFlow<List<Article>>(emptyList())
    val allarticle:StateFlow<List<Article>>
        get()=_allarticle
    suspend fun upsertArticle(article: Article){
        database.articledao().upsertArticle(article)
    }
    suspend fun deleteArticle(article: Article){
        database.articledao().delete(article)
    }
     suspend fun allArticle(){
       database.articledao().allArticle().collect{
           _allarticle.value=it
       }
    }
}