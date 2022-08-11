package com.udemycourse.areaderapp.screens.book_search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.udemycourse.areaderapp.components.AppBar

@Composable
fun SearchScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            AppBar(title = "Search Books", navController = navController)
        },
        content = {
            SearchContent(navController = navController)
        }
    )
}