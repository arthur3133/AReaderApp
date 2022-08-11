package com.udemycourse.areaderapp.repository

import com.udemycourse.areaderapp.data.Resource
import com.udemycourse.areaderapp.model.Item
import com.udemycourse.areaderapp.remote.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val booksApi: BooksApi) {

    suspend fun getAllBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = true)
            val itemList = booksApi.getAllBooks(searchQuery).items
            Resource.Success(itemList)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred.")
        }
    }

    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return try {
            Resource.Loading(data = true)
            val item = booksApi.getBookInfo(bookId)
            Resource.Success(item)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred.")
        }
    }
}