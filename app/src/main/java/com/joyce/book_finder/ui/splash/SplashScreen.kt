package com.joyce.book_finder.ui.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.joyce.book_finder.R
import com.joyce.book_finder.navigation.Screen
import com.joyce.book_finder.theme.BookFinderTheme
import com.joyce.book_finder.theme.PRIMARY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavHostController) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(3000)
        navController.navigate(Screen.Login.route)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(PRIMARY),
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "@null")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    BookFinderTheme {
        SplashScreen(rememberNavController())
    }
}