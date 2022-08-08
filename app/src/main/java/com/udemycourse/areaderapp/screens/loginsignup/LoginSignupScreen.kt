package com.udemycourse.areaderapp.screens.loginsignup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.udemycourse.areaderapp.components.AppName
import com.udemycourse.areaderapp.components.EmailInputField
import com.udemycourse.areaderapp.components.PasswordInputField

@Composable
fun LoginSignupScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val showLoginForm = rememberSaveable {
            mutableStateOf(true)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppName()
            if (showLoginForm.value) {
                UserForm(
                    enable = false,
                    isCreateAccount = false,
                    onDone = { email, password -> }
                )
            } else {
                UserForm(
                    enable = true,
                    isCreateAccount = true,
                    onDone = { email, password -> }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = if (showLoginForm.value) "New User?" else "Already User?")
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable { showLoginForm.value = !showLoginForm.value },
                    text = if (showLoginForm.value) "Sign up" else "Login",
                    color = MaterialTheme.colors.secondaryVariant,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    enable: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit
) {
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val valid = remember(emailState.value, passwordState.value) {
        emailState.value.trim().isNotEmpty() && passwordState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val modifier = Modifier
        .height(280.dp)
        .verticalScroll(rememberScrollState())
        .background(MaterialTheme.colors.background)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (isCreateAccount)
            Text(
                text = "Please enter a valid email and password that is at least 6 characters",
                modifier = Modifier.padding(6.dp),
                fontWeight = FontWeight.Bold
            )
        EmailInputField(
            valueState = emailState,
            labelId = "Email",
            keyboardType = KeyboardType.Email,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            }
        )
        PasswordInputField(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            valueState = passwordState,
            labelId = "Password",
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
            }
        )
        SubmitButton(
            text = if (isCreateAccount) "Create Account" else "Login",
            validInputs = valid,
            onClick = {
                onDone(emailState.value.trim(), passwordState.value.trim())
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun SubmitButton(text: String, validInputs: Boolean, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        shape = CircleShape,
        enabled = validInputs
    ) {
        if (validInputs) {
            CircularProgressIndicator(modifier = Modifier.size(25.dp))
        } else {
            Text(
                text = text,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
