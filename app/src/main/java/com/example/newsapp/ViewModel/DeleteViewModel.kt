package com.example.newsapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Repository.RoomRepository
import com.example.newsapp.Room.ArticleDatabase
import com.example.newsapp.module.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DeleteViewModel @Inject constructor(private val roomRepository: RoomRepository) :ViewModel() {
    fun deleteArticle(article: Article) {
        viewModelScope.launch (Dispatchers.IO){
            roomRepository.deleteArticle(article)
        }
    }
}