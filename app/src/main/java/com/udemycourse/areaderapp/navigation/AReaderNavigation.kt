package com.udemycourse.areaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udemycourse.areaderapp.screens.home.HomeScreen
import com.udemycourse.areaderapp.screens.loginsignup.LoginSignupScreen
import com.udemycourse.areaderapp.screens.splash.SplashScreen

@Composable
fun AReaderNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, 
        startDestination = AReaderScreen.SplashScreen.name
    ) {
        composable(
            route = AReaderScreen.SplashScreen.name
        ) {
           SplashScreen(navController = navController) 
        }
        composable(
            route = AReaderScreen.LoginSignupScreen.name
        ) {
            LoginSignupScreen(navController = navController)
        }
        composable(
            route = AReaderScreen.HomeScreen.name
        ) {
            HomeScreen(navController = navController)
        }
    }
}