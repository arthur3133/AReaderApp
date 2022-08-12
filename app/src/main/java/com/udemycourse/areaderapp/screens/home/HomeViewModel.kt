package com.udemycourse.areaderapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemycourse.areaderapp.data.Resource
import com.udemycourse.areaderapp.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: FirestoreRepository): ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state : StateFlow<HomeState> = _state

    fun getAllBooks() {
        viewModelScope.launch {
            when(val result = repository.getAllBooksFromFirestore()) {
                is Resource.Loading -> {
                   _state.value = HomeState(loading = true)
                }
                is Resource.Success -> {
                    _state.value = HomeState(loading = false, bookList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = HomeState(loading = false, error = result.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}