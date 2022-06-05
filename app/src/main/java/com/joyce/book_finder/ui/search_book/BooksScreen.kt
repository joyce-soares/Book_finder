package com.joyce.book_finder.ui.search_book

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.joyce.book_finder.customViews.ButtonProgress
import com.joyce.book_finder.customViews.TextFieldCustom
import com.joyce.book_finder.theme.PRIMARY
import com.joyce.book_finder.ui.login.LoginScreen
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BooksScreen(booksVM: BooksViewModel) {
    val state by booksVM.state.collectAsState()
    BookContent(booksVM = booksVM, state = state)
}

@Composable
fun BookContent(booksVM: BooksViewModel, state: BooksState?) {
    val isLoading by booksVM.loading.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val fieldValue = remember {
            mutableStateOf("")
        }
        TextField(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .fillMaxWidth(),
            singleLine = true,
            label = { Text(text = "DIGITE AUTOR OU NOME DO LIVRO") },
            value = fieldValue.value,
            onValueChange = {
                fieldValue.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PRIMARY,
                unfocusedIndicatorColor = PRIMARY
            ),

            )
        Spacer(modifier = Modifier.height(26.dp))
        ButtonProgress(
            isLoading = isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            text = "Procurar",
            loadingText = "Procurando...",
            onClicked = { booksVM.getAllBooks(fieldValue.value.trim())  }
        )
        Spacer(modifier = Modifier.height(10.dp))
        when(state){
            is BooksState.Success -> {
                //LazyColumn(content = )
            }
            is BooksState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = PRIMARY
                )
            }
            is BooksState.Error -> {
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
            }
            
        }
    }
}

@Preview
@Composable
fun BooksScreenPreview() {
   // BookContent(getViewModel())
}