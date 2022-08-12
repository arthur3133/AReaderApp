package com.udemycourse.areaderapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AppName(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(10.dp),
        text = "A. Reader",
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(alpha = 0.5f)
    )
}

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it},
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        label = { Text(text = labelId) },
        textStyle = TextStyle(
            color = MaterialTheme.colors.onBackground,
            fontSize = 18.sp
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        enabled = enabled
    )
}

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean = true,
    passwordVisibility: MutableState<Boolean>,
    keyboardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it},
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        label = { Text(text = labelId) },
        textStyle = TextStyle(
            color = MaterialTheme.colors.onBackground,
            fontSize = 18.sp
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        enabled = enabled,
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                Icons.Default.Close
            }
        }
    )
}

@Composable
fun AppBar(
    title: String,
    titleColor: Color = Color.Red,
    tint: Color = Color.Red,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                color = titleColor.copy(0.7f)
            )
        },
        elevation = AppBarDefaults.TopAppBarElevation,
        backgroundColor = Color.Transparent,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back_icon",
                    tint = tint.copy(alpha = 0.7f)
                )
            }
        }
    )
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchQueryState: MutableState<String>,
    label: String,
    maxLines: Int = 1,
    singleLine: Boolean = false,
    onSearchTriggered: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQueryState.value,
        onValueChange = {
            searchQueryState.value = it
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        label = {
            Text(text = label)
        },
        textStyle = TextStyle(color = MaterialTheme.colors.onBackground, fontSize = 18.sp),
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchTriggered(searchQueryState.value.trim())
            }
        )
    )
}

@Composable
fun RoundButton(
    label: String,
    radius: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clip(
            RoundedCornerShape(
            topStartPercent = radius,
            bottomEndPercent = radius)
        ),
        color = Color(0xFF92CBDF)
    ) {
        Column(
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)
                .clickable { onClick() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = label,
                style = TextStyle(color = Color.White, fontSize = 15.sp)
            )
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