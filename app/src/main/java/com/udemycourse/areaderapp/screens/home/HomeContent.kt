package com.udemycourse.areaderapp.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.udemycourse.areaderapp.components.RoundButton
import com.udemycourse.areaderapp.model.MBook
import com.udemycourse.areaderapp.navigation.AReaderScreen

@Composable
fun HomeContent(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = true) {
        homeViewModel.getAllBooks()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        val displayName = FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SectionName(title = "Your reading \n activity right now...")
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               IconButton(onClick = { /*TODO*/ }) {
                   Icon(
                       imageVector = Icons.Default.AccountCircle,
                       contentDescription = "account_icon",
                       tint = MaterialTheme.colors.onBackground,
                       modifier = Modifier.size(50.dp)
                   )
               }
                Text(
                    text = displayName.toString(),
                    style = MaterialTheme.typography.overline,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    fontSize = 15.sp
                )

            }
        }
        ReadingRightNowArea(navController = navController)
        Spacer(modifier = Modifier.height(10.dp))
        SectionName(title = "Reading List")
        val state by homeViewModel.state.collectAsState()
        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        } else {
            val bookList = state.bookList.filter {
                it.userId == FirebaseAuth.getInstance().currentUser?.uid.toString()
            }
            ReadingListArea(books = bookList, navController = navController)
        }
    }
}

@Composable
fun SectionName(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ReadingRightNowArea(books: List<MBook> = emptyList(), navController: NavController) {
    BookCard(book = MBook(title = "Android", authors = ";fkjdsfkdsfkd", notes = "vd;kds;fkdsl", photoUrl = "http://books.google.com/books/content?id=aYpoDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"), navController = navController)
}

@Composable
fun ReadingListArea(books: List<MBook>, navController: NavController) {
    LazyRow(
        modifier = Modifier
            .padding(10.dp)
    ) {
        items(books) { book ->
            BookCard(book = book, navController = navController)
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookCard(book: MBook, navController: NavController) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(250.dp)
            .padding(10.dp),
        onClick = {
            navController.navigate(AReaderScreen.BookUpdateScreen.name + "/${book.googleBookId}")
        },
        shape = RoundedCornerShape(29.dp),
        elevation = 6.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
           ){
               val imageUrl = book.photoUrl?.trim()
               AsyncImage(
                   model = ImageRequest.Builder(LocalContext.current)
                       .data(imageUrl)
                       .crossfade(true)
                       .build(),
                   contentDescription = "book_image",
                   contentScale = ContentScale.Crop,
                   modifier = Modifier
                       .width(100.dp)
                       .height(140.dp)
               )
               Column(
                   verticalArrangement = Arrangement.Top,
                   horizontalAlignment = Alignment.CenterHorizontally,
                   modifier = Modifier.padding(top = 25.dp, end = 6.dp)
               ) {
                   Icon(
                       imageVector = Icons.Default.FavoriteBorder,
                       contentDescription = "favorite_icon",
                       modifier = Modifier.padding(bottom = 1.dp)
                   )
                   BookRating(3.5)
               }
           }
            Text(
                modifier = Modifier.padding(4.dp),
                text = book.title.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = book.authors.toString(),
                style = MaterialTheme.typography.caption
            )
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                RoundButton(label = "Reading", radius = 70, onClick = {})
            }
        }
    }
}

@Composable
fun BookRating(rating: Double) {
    Surface(
        elevation = 6.dp,
        shape = CircleShape,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "star_icon",
                    tint = MaterialTheme.colors.onBackground
                )
            }
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = rating.toString(),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}