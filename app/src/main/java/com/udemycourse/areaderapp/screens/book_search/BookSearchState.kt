package com.udemycourse.areaderapp.screens.book_search

import com.udemycourse.areaderapp.model.Item

data class BookSearchState(
    val loading: Boolean = true,
    val itemList: List<Item> = emptyList(),
    val error: String = ""
)
