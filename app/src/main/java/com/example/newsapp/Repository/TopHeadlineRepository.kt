package com.example.newsapp.Repository

import android.util.Log
import com.example.newsapp.Api.NewsApi
import com.example.newsapp.Constants.Contants
import com.example.newsapp.Room.ArticleDatabase
import com.example.newsapp.module.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TopHeadlineRepository @Inject constructor(
    private val newsApi: NewsApi
) {
    private val _news = MutableStateFlow<News?>(null)
    val news: StateFlow<News?> get() = _news

    suspend fun getTopHeadlines(country: String,category:String) {
        val response = newsApi.getTopHealines(country,category, 2, Contants.api_key)
        if (response.isSuccessful) {
            response.body()?.let { fetchedNews ->
                // Filter articles with non-null values
                val filteredArticles = fetchedNews.articles.filter { article ->
                            article.title != null &&
                            article.description != null &&
                            article.publishedAt != null &&
                            article.urlToImage != null &&
                            article.url != null
                }
                _news.emit(fetchedNews.copy(articles = filteredArticles))
              //  Log.d("HEADLINE EMIT VALUE",_news.value.toString())
            }
        }
        else{Log.d("HEADLINE EMIT VALUE","Can not fetching news")
        }
    }
}
