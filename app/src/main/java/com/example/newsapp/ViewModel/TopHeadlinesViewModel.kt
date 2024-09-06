package com.example.newsapp.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Repository.TopHeadlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository):ViewModel() {
    val news=topHeadlineRepository.news
    private val _category = MutableStateFlow("science")
//    val category:StateFlow<String>
//        get() = _category
    fun setvalue(category:String){
        _category.value=category
    }
    init {
        viewModelScope.launch(Dispatchers.IO) {
            _category.collect{
                topHeadlineRepository.getTopHeadlines("US",it)
            }
        }
    }
}