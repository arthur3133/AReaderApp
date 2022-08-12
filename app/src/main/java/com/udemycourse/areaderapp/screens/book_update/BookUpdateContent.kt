package com.udemycourse.areaderapp.screens.book_update

import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.udemycourse.areaderapp.R
import com.udemycourse.areaderapp.components.RoundButton
import com.udemycourse.areaderapp.model.MBook
import com.udemycourse.areaderapp.navigation.AReaderScreen
import com.udemycourse.areaderapp.screens.book_info.saveBookInfoToFirestore
import com.udemycourse.areaderapp.utils.Constants

@Composable
fun BookUpdateContent(
    navController: NavController = rememberNavController(),
    bookUpdateViewModel: BookUpdateViewModel = hiltViewModel(),
    googleBookId: String
) {
    LaunchedEffect(key1 = true) {
        bookUpdateViewModel.getBook(googleBookId = googleBookId)
    }
    val noteText = remember {
        mutableStateOf("")
    }
    val isStartReading = remember {
        mutableStateOf(false)
    }
    val isFinishReading = remember {
        mutableStateOf(false)
    }
    val ratingState = remember {
        mutableStateOf(0)
    }

    val context = LocalContext.current
    
    val openDialog = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val state by bookUpdateViewModel.state.collectAsState()
        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        } else {
            val book = state.book
            Card(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 20.dp)
                    .fillMaxWidth()
                    .height(150.dp),
                elevation = 4.dp,
                shape = CircleShape
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(book?.photoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "book_image",
                        modifier = Modifier
                            .width(100.dp)
                            .height(120.dp)
                            .clip(
                                RoundedCornerShape(topStart = 120.dp, topEnd = 10.dp)
                            )
                    )
                    Column(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "${book?.title}",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${book?.authors}",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            text = "${book?.publishedDate}",
                            style = MaterialTheme.typography.subtitle1,
                        )
                    }
                }
            }
            if (book != null) {
                NotesTextField(book = book, onDone = {
                    noteText.value = it
                })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(
                    onClick = { isStartReading.value = true },
                    enabled = book?.startedReading == null
                ) {
                    if (book?.startedReading == null) {
                        if (!isStartReading.value) {
                            Text(
                                text = "Start Reading"
                            )
                        } else {
                            Text(
                                text = "Started Reading",
                                modifier = Modifier.alpha(0.6f),
                                color = Color.Red.copy(0.5f)
                            )
                        }
                    } else {
                        Text(
                            text = "Started on: ${Constants.formatDate(book.startedReading!!)}"
                        )
                    }
                }
                TextButton(
                    onClick = { isFinishReading.value = true },
                    enabled = book?.finishedReading == null
                ) {
                    if (book?.finishedReading == null) {
                        if (!isFinishReading.value) {
                            Text(
                                text = "Mark as Read"
                            )
                        } else {
                            Text(
                                text = "Finished Reading"
                            )
                        }
                    } else {
                        Text(
                            text = "Finished on: ${Constants.formatDate(book.finishedReading!!)}"
                        )
                    }
                }
            }
            Text(
                text = "Rating",
                modifier = Modifier.align(CenterHorizontally)
            )
            book?.rating?.toInt()?.let {
                RatingBar(rating = it, onPressed = { rating ->
                    ratingState.value = rating
                })
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val updateNoteText = book?.notes != noteText.value.trim()
                val updateStartReading = if (isStartReading.value) Timestamp.now() else book?.startedReading
                val updateFinishReading = if (isFinishReading.value) Timestamp.now() else book?.finishedReading
                val updateRating = book?.rating?.toInt() != ratingState.value

                val bookUpdate = updateNoteText || isStartReading.value || isFinishReading.value || updateRating

                val bookToUpdate = hashMapOf(
                    "notes" to noteText.value,
                    "started_reading" to updateStartReading,
                    "finished_reading" to updateFinishReading,
                    "rating" to ratingState.value
                ).toMap()
                RoundButton(label = "Update", radius = 29, onClick = {
                    if (bookUpdate) {
                        FirebaseFirestore.getInstance().collection("books")
                            .document(book?.id.toString())
                            .update(bookToUpdate)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Book Updated Successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.navigate(AReaderScreen.HomeScreen.name)
                                }
                            }
                    }
                })
                RoundButton(label = "Delete", radius = 29, onClick = {
                    openDialog.value = true
                })

                if (openDialog.value) {
                    ShowDialog(openDialog = openDialog, onYesClicked = {
                        book?.id?.let {
                            FirebaseFirestore.getInstance().collection("books")
                                .document(it).delete()
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        openDialog.value = false
                                        navController.navigate(AReaderScreen.HomeScreen.name)
                                    }
                                }
                        }
                    })
                }
            }
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotesTextField(
    book: MBook,
    onDone: (String) -> Unit
) {
    val notes = book?.notes.toString().ifEmpty { "Not thoughts available." }

    val valueState = rememberSaveable {
        mutableStateOf(notes)
    }

    val valid = remember(valueState.value) {
        valueState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        textStyle = TextStyle(fontSize = 20.sp),
        label = {
            Text(text = "Enter your thoughts")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (!valid)
                    return@KeyboardActions
                keyboardController?.hide()
                onDone(valueState.value.trim())
            }
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    rating: Int,
    onPressed: (Int) -> Unit
) {
   var ratingState by remember {
       mutableStateOf(rating)
   }

    var selected by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(
        targetValue = if (selected) 42.dp else 34.dp,
        spring(Spring.DampingRatioMediumBouncy)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                contentDescription = "star_icon",
                modifier = Modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                onPressed(i)
                                ratingState = i
                            }
                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
            )
        }
    }
}

@Composable
fun ShowDialog(
    openDialog: MutableState<Boolean>,
    onYesClicked: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {
                Text(text = "Delete Book")
            },
            text = {
                Text(text = "Are you sure want to delete this book? This action is not reversible")
            },
            buttons = {
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.End) {
                    Button(onClick = {
                        onYesClicked()
                    }) {
                        Text(text = "Yes")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(onClick = {
                        openDialog.value = false
                    }) {
                        Text(text = "No")
                    }
                }
            }
        ) 
    }
}