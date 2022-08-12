package com.udemycourse.areaderapp.screens.home

import com.udemycourse.areaderapp.model.MBook

data class HomeState(
    val loading: Boolean = false,
    val bookList: List<MBook> = emptyList(),
    val error: String = ""
)
