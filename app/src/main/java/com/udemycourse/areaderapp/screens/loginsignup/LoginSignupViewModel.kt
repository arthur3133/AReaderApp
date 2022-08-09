package com.udemycourse.areaderapp.screens.loginsignup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.udemycourse.areaderapp.data.MUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginSignupViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(email: String, password: String, goToHome: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            goToHome()
                        } else {
                            Log.d(
                                "Firebase Login",
                                "signInWithEmailAndPassword: ${task.result}"
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.d("Firebase Login Error", "signInWithEmailAndPassword: ${e.localizedMessage}")
            }
        }
    }

    fun createUserWithEmailAndPassword(email: String, password: String, goToHome: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val displayName = task.result.user?.email?.split("@")?.get(0)
                            saveUserToFireStore(displayName)
                            goToHome()
                        } else {
                            Log.d(
                                "Firebase Signup",
                                "createAccountWithEmailAndPassword: ${task.result}"
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.d("Firebase Signup Error", "createAccountWithEmailAndPassword: ${e.localizedMessage}")
            }
        }
    }

    private fun saveUserToFireStore(displayName: String?) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val user = MUser(
            userId = userId,
            displayName = displayName.toString(),
            profession = "Android Developer",
            avatarUrl = "",
            quote = "Life is going good"
        ).toMap()
        FirebaseFirestore.getInstance().collection("users").add(user)
    }
}