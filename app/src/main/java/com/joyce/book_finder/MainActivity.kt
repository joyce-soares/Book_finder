package com.joyce.book_finder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.joyce.book_finder.navigation.NavigationComponent

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold {
                navController = rememberNavController()
                NavigationComponent(navController = navController)
            }
        }
    }
}
