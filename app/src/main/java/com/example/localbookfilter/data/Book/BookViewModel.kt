package com.example.localbookfilter.data.Book

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.localbookfilter.HttpApiService
import com.example.localbookfilter.MyApplication
import com.example.localbookfilter.data.Author.Author
import com.example.localbookfilter.data.Author.AuthorRepo
import com.example.localbookfilter.data.Author.AuthorViewModel
import com.example.localbookfilter.data.DemoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application): AndroidViewModel(application) {
    private val repo: BookRepo

    init {
        val bookDao = DemoDatabase.getDb(application).bookDao()
        repo = BookRepo(bookDao)
    }

    fun addBook(book: Book){
        viewModelScope.launch  (Dispatchers.IO ){
            repo.addBook(book)
        }
    }

    fun readData(authorName: String): LiveData<List<Book>>{
        return repo.readData(authorName)
    }

    fun insertData(myAuthorViewModel: AuthorViewModel, httpApiService: HttpApiService){

        viewModelScope.launch(Dispatchers.IO) {

            val jsonRes = httpApiService.getAllBooks()
            for (books in jsonRes) {
                val book = Book(
                    0,
                    books.title,
                    books.imageLink,
                    books.language,
                    books.link,
                    books.pages,
                    books.year,
                    getAuthId(myAuthorViewModel, books.author)
                )

                addBook(book)
            }

        }
    }

    private fun getAuthId(mAuthorViewModel: AuthorViewModel, author: String): Int {
        return mAuthorViewModel.getIdByAuthName(author)
    }

}