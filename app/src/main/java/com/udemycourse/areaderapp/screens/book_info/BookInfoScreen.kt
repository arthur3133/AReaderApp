package com.udemycourse.areaderapp.screens.book_info

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.udemycourse.areaderapp.components.AppBar

@Composable
fun BookInfoScreen(navController: NavController, bookId: String?) {
    Scaffold(
        topBar = {
            AppBar(title = "Book Info", navController = navController)
        },
        content = {
            BookInfoContent(navController = navController, bookId = bookId)
        }
    )
}