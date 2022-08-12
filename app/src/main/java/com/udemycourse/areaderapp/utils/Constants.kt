package com.udemycourse.areaderapp.utils

import android.icu.text.DateFormat
import android.os.Build
import com.google.firebase.Timestamp

object Constants {

    // https://www.googleapis.com/books/v1/volumes?q=android
    const val BASE_URL = "https://www.googleapis.com/books/v1/"


    fun formatDate(timestamp: Timestamp): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DateFormat.getDateInstance().format(timestamp.toDate()).toString().split(",")[0]
        } else {
            TODO("VERSION.SDK_INT < N")
        }
    }
}