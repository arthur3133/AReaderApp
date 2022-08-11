package com.udemycourse.areaderapp.screens.book_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemycourse.areaderapp.data.Resource
import com.udemycourse.areaderapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {
    private val _state = MutableStateFlow(BookSearchState())
    val state: StateFlow<BookSearchState> = _state

    init {
        getAllBooks("android")
    }

    fun getAllBooks(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = repository.getAllBooks(searchQuery)) {
                is Resource.Loading -> {
                    _state.value = BookSearchState(loading = true)
                }
                is Resource.Success -> {
                    _state.value = BookSearchState(loading = false, itemList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = BookSearchState(loading = false, error = result.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}