package com.udemycourse.areaderapp.screens.book_update

import com.udemycourse.areaderapp.model.MBook

data class BookUpdateState(
    val loading: Boolean = false,
    val book: MBook?= null,
    val error: String = ""
)
