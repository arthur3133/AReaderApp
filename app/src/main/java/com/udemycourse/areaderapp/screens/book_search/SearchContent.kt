package com.udemycourse.areaderapp.screens.book_search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.udemycourse.areaderapp.components.SearchTextField
import com.udemycourse.areaderapp.model.Item

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchContent(navController: NavController, bookSearchViewModel: BookSearchViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top
    ) {
        val searchQueryState = remember {
            mutableStateOf("")
        }
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        SearchTextField(
            searchQueryState = searchQueryState,
            label = "Search",
            onSearchTriggered = {
                if (valid) {
                    keyboardController?.hide()
                    bookSearchViewModel.getAllBooks(searchQueryState.value.trim())
                    searchQueryState.value = ""
                }
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        val state by bookSearchViewModel.state.collectAsState()
        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
        } else {
            LazyColumn {
                items(state.itemList) { item ->
                    BookRow(book = item, onClicked = {})
                }
            }
        }
    }
}

@Composable
fun BookRow(
    book: Item,
    onClicked: (Item) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 5.dp)
            .clickable { onClicked(book) },
        elevation = 6.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            val imageUrl = book.volumeInfo.imageLinks.thumbnail.trim()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "book_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(100.dp)
                    .padding(end = 8.dp)
            )
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(top = 3.dp)
            ) {
                Text(
                    text = book.volumeInfo.title,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Authors: ${book.volumeInfo.authors}",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = "Date: ${book.volumeInfo.publishedDate}",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = "${book.volumeInfo.categories}",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}
