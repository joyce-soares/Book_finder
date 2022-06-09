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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.joyce.book_finder.R
import com.joyce.book_finder.customViews.ButtonProgress
import com.joyce.book_finder.models.*
import com.joyce.book_finder.theme.PRIMARY

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
            onClicked = { booksVM.getAllBooks(fieldValue.text.trim()) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        when (state) {
            is BooksState.Success -> {
                val books = remember { state.books }
                RenderList(books = books)
            }
            is BooksState.Error -> {
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}

@Composable
fun RenderList(books: ResponseGetBooks) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(books.books) { book ->
            BookContent(book)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun BookContent(book: BookItem?) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ) {
        val (autor, titulo, desc, image, txtAu, txtTi, txtDesc) = createRefs()


        if (book != null) {
            val url = book.imagens.imagem_primeira_capa.pequena
            val painter = rememberImagePainter(data = url, builder = {})
            Image(
                painter = painter, contentDescription = "",
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .padding(top = 10.dp, start = 10.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
            )

            val context = LocalContext.current
            Text(
                text = "Autor: ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 10.dp, start = 8.dp)
                    .constrainAs(txtAu) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end)
                    },
            )
            Text(
                text = "NULLO",
                modifier = Modifier
                    .padding(top = 10.dp)
                    .constrainAs(autor) {
                        top.linkTo(parent.top)
                        start.linkTo(autor.end)
                    },
            )
            Text(
                text = "Título: ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp)
                    .constrainAs(txtTi) {
                        top.linkTo(autor.bottom)
                        start.linkTo(image.end)
                    },
            )
            Text(
                text = book.titulo ?: "",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .constrainAs(titulo) {
                        top.linkTo(txtTi.top)
                        start.linkTo(txtTi.end)
                        bottom.linkTo(txtTi.bottom)
                    },
            )
            if (!book.sumario.isNullOrEmpty()) {
                Text(
                    text = "Descrição: ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp)
                        .constrainAs(txtDesc) {
                            top.linkTo(txtTi.bottom)
                            start.linkTo(image.end)
                        },
                )
                Text(
                    textAlign = TextAlign.Left,
                    maxLines = 3,
                    text =  book.sumario,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 15.dp)
                        .constrainAs(desc) {
                            top.linkTo(txtDesc.top)
                            start.linkTo(txtDesc.end)
                        },
                )
            }
        }
    }
}

@Preview
@Composable
fun BooksScreenPreview() {
    val co = ContribuicaoItem(
        nome = "James",
        sobrenome = "Joyce"
    )
    val imagens = Imagens(imagem_primeira_capa("https://s.dicio.com.br/descricao.jpg", "", ""))
    val book = BookItem(
        titulo = "O Ladrao e a policia",
        sumario = "A descrição é a enumeração das características próprias dos seres, coisas, cenários, ambientes, costumes, impressões etc. A visão, o tato, a audição, o olfato e o paladar constituem a base da descrição.",
        contribuicao = listOf(co),
        imagens = imagens
    )
    BookContent(book)
}


