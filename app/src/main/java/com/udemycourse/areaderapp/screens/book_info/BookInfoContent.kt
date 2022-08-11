package com.udemycourse.areaderapp.screens.book_info

import android.text.Html
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.udemycourse.areaderapp.components.RoundButton
import com.udemycourse.areaderapp.model.MBook
import com.udemycourse.areaderapp.model.VolumeInfo
import com.udemycourse.areaderapp.navigation.AReaderScreen

@Composable
fun BookInfoContent(
    navController: NavController,
    bookId: String?,
    bookInfoViewModel: BookInfoViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        bookInfoViewModel.getBookInfo(bookId!!)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val state by bookInfoViewModel.state.collectAsState()
        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
        } else {
            val bookInfo = state.item?.volumeInfo
            val googleBookId = state.item?.id
            bookInfo?.imageLinks?.thumbnail?.let { ImageCard(imageUrl = it) }
            Text(
                text = "${bookInfo?.title}",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = "Authors: ${bookInfo?.authors}",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = "Publisher: ${bookInfo?.publisher}",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = "Date: ${bookInfo?.publishedDate}",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = "${bookInfo?.categories}",
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle2,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(6.dp)
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(6.dp),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.onBackground)
            ) {
                val cleanDescription = HtmlCompat.fromHtml(bookInfo?.description!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
                LazyColumn {
                    item {
                        Text(
                            text = "$cleanDescription",
                            color = MaterialTheme.colors.onBackground,
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                RoundButton(label = "Save", radius = 29, onClick = {
                    val mBook = MBook(
                        title = bookInfo?.title,
                        authors = bookInfo?.authors.toString(),
                        notes = "",
                        photoUrl = bookInfo?.imageLinks?.thumbnail,
                        categories = bookInfo?.categories.toString(),
                        publishedDate = bookInfo?.publishedDate,
                        rating = 0.0,
                        description = bookInfo?.description,
                        pageCount = bookInfo?.pageCount.toString(),
                        userId = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                        googleBookId = googleBookId
                    )
                    saveBookInfoToFirestore(mBook = mBook, navController = navController)
                })
                RoundButton(label = "Cancel", radius = 29, onClick = {
                    navController.popBackStack()
                })
            }
        }
    }
}

fun saveBookInfoToFirestore(mBook: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val collection = db.collection("books")
    collection.add(mBook)
        .addOnSuccessListener { documentRef ->
            val docId = documentRef.id
            collection.document(docId).update(
                hashMapOf("id" to docId) as HashMap<String, Any>)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate(AReaderScreen.HomeScreen.name) {
                            popUpTo(AReaderScreen.BookInfoScreen.name) {
                                inclusive = true
                            }
                        }
                    }
                }

        }
}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    size: Dp = 150.dp,
    backgroundColor: Color = MaterialTheme.colors.background,
    elevation: Dp = 4.dp,
    shape: Shape = CircleShape,
    imageUrl: String,
) {
    Card(
        modifier = modifier
            .size(size)
            .padding(6.dp),
        shape = shape,
        elevation = elevation,
        backgroundColor = backgroundColor
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(), 
            contentDescription = "book_image",
            contentScale = ContentScale.Crop
        )
    }
}