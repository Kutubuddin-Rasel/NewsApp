package com.example.newsapp.Repository

import com.example.newsapp.Api.NewsApi
import com.example.newsapp.Constants.Contants
import com.example.newsapp.module.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class EveryNewsRepository @Inject constructor(private val newsApi: NewsApi) {
    private val _evereyNews = MutableStateFlow<News?>(null)
    val everyNews:StateFlow<News?>
        get() = _evereyNews
   suspend fun getEverything(topic:String,sortBy:String){
      val response=  newsApi.getEverything(topic,sortBy,20,Contants.api_key)
       if(response.isSuccessful)
       { response.body()?.let { newNews->
               val filterNews = newNews.articles.filter {article->
                           article.author != null &&
                           article.title != null &&
                           article.description != null &&
                           article.publishedAt != null &&
                           article.urlToImage != null &&
                           article.url != null
               }
               _evereyNews.emit(newNews.copy(articles = filterNews))
           }
       }
    }
}