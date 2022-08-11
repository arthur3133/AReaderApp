package com.udemycourse.areaderapp.screens.book_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemycourse.areaderapp.data.Resource
import com.udemycourse.areaderapp.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookInfoViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {
    private val _bookInfoState = MutableStateFlow(BookInfoState())
    val bookInfoState: StateFlow<BookInfoState> = _bookInfoState

    fun getBookInfo(bookId: String) {
        viewModelScope.launch {
            when (val result = repository.getBookInfo(bookId)) {
                is Resource.Loading -> {
                    _bookInfoState.value = BookInfoState(loading = true)
                }
                is Resource.Success -> {
                    _bookInfoState.value = BookInfoState(loading = false, item = result.data)
                }
                is Resource.Error -> {
                    _bookInfoState.value = BookInfoState(loading = false, error = result.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}