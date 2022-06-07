package com.joyce.book_finder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.joyce.book_finder.navigation.NavigationComponent
import com.joyce.book_finder.ui.login.LoginViewModel
import com.joyce.book_finder.ui.search_book.BooksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val loginVM by viewModel<LoginViewModel>()
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold {
                navController = rememberNavController()
                NavigationComponent(navController = navController, loginVM = loginVM)
            }
        }
    }
}
