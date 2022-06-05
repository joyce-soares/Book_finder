package com.joyce.book_finder.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.joyce.book_finder.MainActivity
import com.joyce.book_finder.R
import com.joyce.book_finder.ui.splash.ui.theme.BookFinderTheme
import com.joyce.book_finder.ui.splash.ui.theme.PRIMARY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookFinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SplashScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            MainActivity.start(applicationContext)
        }
    }
}

@Composable
fun SplashScreen() {
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
        SplashScreen()
    }
}