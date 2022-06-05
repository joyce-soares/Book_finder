package com.joyce.book_finder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.joyce.book_finder.ui.login.LoginScreen
import com.joyce.book_finder.ui.search_book.BooksScreen

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("books") {
            BooksScreen()
        }
    }
}