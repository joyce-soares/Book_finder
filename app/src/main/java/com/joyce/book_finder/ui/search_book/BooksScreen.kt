package com.joyce.book_finder.ui.search_book

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.joyce.book_finder.R
import com.joyce.book_finder.customViews.ButtonProgress
import com.joyce.book_finder.models.BookItem
import com.joyce.book_finder.models.ResponseGetBooks
import com.joyce.book_finder.theme.PRIMARY

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BooksScreen(booksVM: BooksViewModel) {
    val state by booksVM.state.collectAsState()
    BooksContent(booksVM = booksVM, state = state)
}

@Composable
fun BooksContent(booksVM: BooksViewModel, state: BooksState?) {
    val isLoading by booksVM.loading.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var fieldValue by remember {
            mutableStateOf(TextFieldValue(""))
        }
        TextField(
            value = fieldValue,
            onValueChange = {
                fieldValue = it
            },
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .fillMaxWidth(),
            singleLine = true,
            label = { Text(text = "DIGITE AUTOR OU NOME DO LIVRO") },
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
            onClicked = { booksVM.getAllBooks(fieldValue.text.trim())  }
        )
        Spacer(modifier = Modifier.height(10.dp))
        when(state){
            is BooksState.Success -> {
                val books = remember { state.books }
                RenderList(books = books)
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
            else -> {}
        }
    }
}

@Composable
fun RenderList(books: ResponseGetBooks){
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ){
        items(books.books){ book ->
            BookContent( book)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BookContent(book: BookItem){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ){
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = "Autor: ${book.contribuicao?.get(0)?.nome} ${book.contribuicao?.get(0)?.sobrenome}")
            Text(text = "Titulo: ${book.titulo}")
            Text(text = "Descricao: ${book.sumario}")
        }
        val url = book.imagens.imagem_primeira_capa.pequena
        val painter = rememberImagePainter(data = url, builder = {})
        Image(
            painter = painter, contentDescription = "",
            modifier = Modifier
                .padding(top = 15.dp, end = 15.dp)
        )
    }
}

@Preview
@Composable
fun BooksScreenPreview() {
//BookContent()
}


