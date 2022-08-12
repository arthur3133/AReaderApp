package com.udemycourse.areaderapp.screens.book_update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udemycourse.areaderapp.data.Resource
import com.udemycourse.areaderapp.repository.FirestoreRepository
import com.udemycourse.areaderapp.screens.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookUpdateViewModel @Inject constructor(private val repository: FirestoreRepository): ViewModel() {
    private val _state = MutableStateFlow(BookUpdateState())
    val state : StateFlow<BookUpdateState> = _state

    fun getBook(googleBookId: String) {
        viewModelScope.launch {
            when(val result = repository.getBook(googleBookId = googleBookId)) {
                is Resource.Loading -> {
                    _state.value = BookUpdateState(loading = true)
                }
                is Resource.Success -> {
                    _state.value = BookUpdateState(loading = false, book = result.data)
                }
                is Resource.Error -> {
                    _state.value = BookUpdateState(loading = false, error = result.message ?: "An unexpected error occurred")
                }
            }
        }
    }
}