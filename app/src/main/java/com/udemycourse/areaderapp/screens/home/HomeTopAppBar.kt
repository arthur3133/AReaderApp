package com.udemycourse.areaderapp.screens.home

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.udemycourse.areaderapp.R
import com.udemycourse.areaderapp.navigation.AReaderScreen

@Composable
fun HomeTopAppBar(
    navController: NavController
) {
    TopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_books),
                contentDescription = "book_icon",
                tint = MaterialTheme.colors.onBackground
            )
        },
        title = {
            Text(
                text = "A. Reader",
                color = Color.Red.copy(alpha = 0.5f),
                style = MaterialTheme.typography.h5
            )
        },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(AReaderScreen.LoginSignupScreen.name) {
                    popUpTo(AReaderScreen.HomeScreen.name) {
                        inclusive = true
                    }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_log_out),
                    contentDescription = "log_out_icon",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}