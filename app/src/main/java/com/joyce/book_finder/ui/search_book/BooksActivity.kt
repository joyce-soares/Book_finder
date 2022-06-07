package com.joyce.book_finder.ui.search_book

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joyce.book_finder.ui.search_book.ui.theme.BookfinderTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class BooksActivity : ComponentActivity() {

    private val booksVM by viewModel<BooksViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookfinderTheme {
                BooksScreen(booksVM = booksVM)

            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, BooksActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookfinderTheme {
        Greeting("Android")
    }
}