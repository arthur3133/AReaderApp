package com.udemycourse.areaderapp.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.scale
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.udemycourse.areaderapp.components.AppName
import com.udemycourse.areaderapp.navigation.AReaderScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    val scale = remember {
       Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }
            )
        )
        delay(2000L)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(AReaderScreen.LoginSignupScreen.name) {
                popUpTo(AReaderScreen.SplashScreen.name) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(AReaderScreen.HomeScreen.name) {
                popUpTo(AReaderScreen.SplashScreen.name) {
                    inclusive = true
                }
            }
        }
    }
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
                modifier = Modifier
                    .size(330.dp)
                    .scale(scale.value),
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