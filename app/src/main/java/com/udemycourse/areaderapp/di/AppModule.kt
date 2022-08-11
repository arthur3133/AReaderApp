package com.udemycourse.areaderapp.di

import com.udemycourse.areaderapp.remote.BooksApi
import com.udemycourse.areaderapp.repository.BookRepository
import com.udemycourse.areaderapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBooksApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBookSearchRepository(booksApi: BooksApi) = BookRepository(booksApi)
}