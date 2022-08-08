package com.udemycourse.areaderapp.screens.splash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.udemycourse.areaderapp.components.AppName

@Composable
fun SplashScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(330.dp),
                color = MaterialTheme.colors.background,
                shape = CircleShape,
                border = BorderStroke(width = 2.dp, color = Color.LightGray)
            ) {
                Column(
                    modifier = Modifier.padding(6.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppName()
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "\"Read. Change Yourself\"",
                        style = MaterialTheme.typography.h5,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}