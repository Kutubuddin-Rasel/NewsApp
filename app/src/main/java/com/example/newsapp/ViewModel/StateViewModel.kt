package com.example.newsapp.ViewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StateViewModel @Inject constructor():ViewModel() {
    private val _state= MutableStateFlow<Boolean>(false)
    val state:StateFlow<Boolean>
        get() = _state
    fun changeState(state:Boolean)
    {
        _state.value=state
    }
}