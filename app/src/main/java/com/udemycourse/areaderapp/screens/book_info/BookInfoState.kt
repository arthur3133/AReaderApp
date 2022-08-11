package com.udemycourse.areaderapp.screens.book_info

import com.udemycourse.areaderapp.model.Item

data class BookInfoState(
    val loading: Boolean = true,
    val item: Item? = null,
    val error: String = ""
)
