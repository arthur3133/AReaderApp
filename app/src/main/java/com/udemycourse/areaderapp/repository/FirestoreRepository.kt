package com.udemycourse.areaderapp.repository

import com.google.firebase.firestore.Query
import com.udemycourse.areaderapp.data.Resource
import com.udemycourse.areaderapp.model.MBook
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor(private val queryBook: Query) {

    suspend fun getAllBooksFromFirestore(): Resource<List<MBook>> {
        return try {
            Resource.Loading(data = true)
            val booksList = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
            Resource.Success(data = booksList)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}