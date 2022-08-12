package com.udemycourse.areaderapp.screens.book_update

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.udemycourse.areaderapp.components.AppBar

@Composable
fun BookUpdateScreen(
    navController: NavController,
    googleBookId: String?
) {
    Scaffold(
        topBar = {
            AppBar(title = "Book Update", navController = navController)
        },
        content = {
            BookUpdateContent(navController = navController, googleBookId = googleBookId!!)
        }
    ) 
}