package com.example.newsapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Repository.EveryNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EveryNewsViewModel @Inject constructor(private val everyNewsRepository: EveryNewsRepository):ViewModel() {
    val everyNews=everyNewsRepository.everyNews
    private val _sortBy=MutableStateFlow("relevancy")
    private val _topic=MutableStateFlow("politics")
    private val _topicname=MutableStateFlow("")
    val topicname:StateFlow<String>
        get() = _topicname
    fun Sort(sort:String){
        _sortBy.value=sort
    }
    fun Topic(topic:String){
        _topicname.value=topic
    }
    fun topic(top:String){
        _topic.value=top
    }
    init {
        viewModelScope.launch(Dispatchers.IO) {
            combine(_topic, _sortBy) { topic, sortBy ->
                topic to sortBy
            }.collect { (topic, sortBy) ->
                everyNewsRepository.getEverything(topic, sortBy)
            }
        }
    }
}