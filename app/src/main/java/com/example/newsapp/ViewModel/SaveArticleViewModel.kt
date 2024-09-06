package com.example.newsapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.size.Dimension
import com.example.newsapp.Repository.RoomRepository
import com.example.newsapp.module.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SaveArticleViewModel @Inject constructor(private val roomRepository: RoomRepository):ViewModel() {
    fun saveArticle(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.upsertArticle(article)
        }
    }
}