package com.udemycourse.areaderapp.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.udemycourse.areaderapp.navigation.AReaderScreen
import com.udemycourse.areaderapp.ui.theme.Purple500

@Composable
fun HomeScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = { HomeTopAppBar(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                              navController.navigate(AReaderScreen.SearchScreen.name)
                    },
                    shape = CircleShape,
                    backgroundColor = Purple500
                ) {
                      Icon(
                          imageVector = Icons.Default.Add,
                          contentDescription = "add_icon",
                          tint = Color.White
                      )
                }
            },
            content = {
                HomeContent(navController = navController)
            }
        )
    }
}