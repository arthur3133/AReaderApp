package com.udemycourse.areaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udemycourse.areaderapp.screens.book_info.BookInfoScreen
import com.udemycourse.areaderapp.screens.home.HomeScreen
import com.udemycourse.areaderapp.screens.loginsignup.LoginSignupScreen
import com.udemycourse.areaderapp.screens.book_search.SearchScreen
import com.udemycourse.areaderapp.screens.book_stats.BookStatsScreen
import com.udemycourse.areaderapp.screens.book_update.BookUpdateScreen
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
        composable(
            route = AReaderScreen.BookSearchScreen.name
        ) {
            SearchScreen(navController = navController)
        }
        composable(
            route = AReaderScreen.BookInfoScreen.name+"/{bookId}",
            arguments = listOf(navArgument("bookId"){
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val bookId = navBackStackEntry.arguments?.getString("bookId")
            BookInfoScreen(navController = navController, bookId = bookId)
        }
        composable(
            route = AReaderScreen.BookUpdateScreen.name+"/{googleBookId}",
            arguments = listOf(navArgument("googleBookId"){
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val googleBookId = navBackStackEntry.arguments?.getString("googleBookId")
            BookUpdateScreen(navController = navController, googleBookId = googleBookId)
        }
        composable(
            route = AReaderScreen.BookStatsScreen.name
        ) {
            BookStatsScreen(navController = navController)
        }
    }
}