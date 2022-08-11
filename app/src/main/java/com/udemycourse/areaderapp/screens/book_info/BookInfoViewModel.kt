package com.udemycourse.areaderapp.screens.book_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemycourse.areaderapp.data.Resource
import com.udemycourse.areaderapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {
    private val _state = MutableStateFlow(BookInfoState())
    val state: StateFlow<BookInfoState> = _state

    fun getBookInfo(bookId: String) {
        viewModelScope.launch {
            when (val result = repository.getBookInfo(bookId)) {
                is Resource.Loading -> {
                    _state.value = BookInfoState(loading = true)
                }
                is Resource.Success -> {
                    _state.value = BookInfoState(loading = false, item = result.data)
                }
                is Resource.Error -> {
                    _state.value = BookInfoState(loading = false, error = result.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}