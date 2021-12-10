package com.example.localbookfilter

import com.example.localbookfilter.data.Book.Book
import retrofit2.http.GET

interface HttpApiService {

    @GET("/books")
    suspend fun getAllBooks(): List<BookResult>

}