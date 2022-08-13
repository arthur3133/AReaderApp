package com.udemycourse.areaderapp.screens.book_stats

import com.udemycourse.areaderapp.model.MBook

data class BookStatsState(
    val loading: Boolean = false,
    val bookList: List<MBook> = emptyList(),
    val error: String = ""
)
