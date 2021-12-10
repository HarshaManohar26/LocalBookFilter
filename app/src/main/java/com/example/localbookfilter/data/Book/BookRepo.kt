package com.example.localbookfilter.data.Book

import androidx.lifecycle.LiveData

class BookRepo(private val bookDao: BookDao) {

    suspend fun addBook(book: Book){
        bookDao.addBook(book)
    }

    fun readData(authorName: String): LiveData<List<Book>>{
        return bookDao.findAllData(authorName)
    }

}