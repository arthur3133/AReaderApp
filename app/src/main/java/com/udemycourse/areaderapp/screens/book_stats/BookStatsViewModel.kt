package com.udemycourse.areaderapp.screens.book_stats

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
class BookStatsViewModel @Inject constructor(private val repository: FirestoreRepository): ViewModel() {
    private val _state = MutableStateFlow(BookStatsState())
    val state : StateFlow<BookStatsState> = _state

    fun getAllBooks() {
        viewModelScope.launch {
            when(val result = repository.getAllBooksFromFirestore()) {
                is Resource.Loading -> {
                   _state.value = BookStatsState(loading = true)
                }
                is Resource.Success -> {
                    _state.value = BookStatsState(loading = false, bookList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = BookStatsState(loading = false, error = result.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}