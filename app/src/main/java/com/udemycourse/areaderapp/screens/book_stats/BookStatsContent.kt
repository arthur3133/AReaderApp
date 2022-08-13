package com.udemycourse.areaderapp.screens.book_stats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.udemycourse.areaderapp.model.MBook

@Composable
fun BookStatsContent(
    bookStatsViewModel: BookStatsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        bookStatsViewModel.getAllBooks()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val displayName = currentUser?.email?.split("@")?.get(0)
        val state by bookStatsViewModel.state.collectAsState()
        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        } else {
            val bookList = state.bookList.filter {
                it.userId == FirebaseAuth.getInstance().currentUser?.uid.toString()
            }
            val readingCount = bookList.filter {
                it.startedReading != null && it.finishedReading == null
            }.size
            val readCount = bookList.filter {
                it.startedReading != null && it.finishedReading != null
            }.size
            val readBookList = bookList.filter {
                it.startedReading != null && it.finishedReading != null
            }
            Text(
                text = "Hi ${displayName?.uppercase()}",
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(10.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = 4.dp,
                backgroundColor = MaterialTheme.colors.background,
                shape = RoundedCornerShape(34.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = "Your Stats", style = MaterialTheme.typography.h5)
                    Divider()
                    Text("You're reading $readingCount books")
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("You've read $readCount books")
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Divider()
            Spacer(modifier = Modifier.height(5.dp))
            if (readBookList.isNotEmpty()) {
                LazyColumn {
                    items(readBookList) { item ->
                        BookRow(book = item)
                    }
                }
            } else {
                Text(text = "Not completed any books...")
            }
        }
    }
}

@Composable
fun BookRow(
    book: MBook,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 5.dp)
            .clickable { },
        elevation = 6.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            val imageUrl = book.photoUrl?.trim()
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = book.title!!,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    if (book.rating!! >= 4.0) {
                        Icon(imageVector = Icons.Default.ThumbUp,
                            contentDescription = "thump_up_icon",
                            tint = Color.Red.copy(0.3f)
                        )
                    }
                }
                Text(
                    text = "Authors: ${book.authors}",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Date: ${book.publishedDate}",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "${book.categories}",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}