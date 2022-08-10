package com.udemycourse.areaderapp.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.udemycourse.areaderapp.components.SearchTextField
import com.udemycourse.areaderapp.data.MBook

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun SearchContent(navController: NavController = rememberNavController()) {
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
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun BookRow(
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        elevation = 6.dp,
        shape = RectangleShape,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            val imageUrl = "http://books.google.com/books/content?id=JGH0DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "book_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(end = 4.dp)
            )
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Android Title",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = "Author: ",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = "Date: ",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}
