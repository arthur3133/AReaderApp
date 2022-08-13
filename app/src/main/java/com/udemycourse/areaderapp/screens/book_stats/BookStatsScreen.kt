package com.udemycourse.areaderapp.screens.book_stats

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.udemycourse.areaderapp.components.AppBar

@Composable
fun BookStatsScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            AppBar(title = "Book Stats", navController = navController)
        },
        content = {
            BookStatsContent()
        }
    )
}