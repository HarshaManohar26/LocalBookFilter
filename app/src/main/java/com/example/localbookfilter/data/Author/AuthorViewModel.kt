package com.example.localbookfilter.data.Author

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.localbookfilter.HttpApiService
import com.example.localbookfilter.data.DemoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorViewModel(application: Application): AndroidViewModel(application) {
    private val repo: AuthorRepo

    init {
        val authorDao = DemoDatabase.getDb(application).authorDao()
        repo = AuthorRepo(authorDao)
    }

    fun addAuthor(author: Author){
        viewModelScope.launch  (Dispatchers.IO ){
            repo.addAuthor(author)
        }
    }

    fun getIdByAuthName(authName: String): Int{
        return repo.getIdByName(authName)
    }

    fun insertData(myAuthorViewModel: AuthorViewModel, httpApiService: HttpApiService){
        viewModelScope.launch(Dispatchers.IO) {

            val jsonRes = httpApiService.getAllBooks()     //http req here

            for (books in jsonRes) {
                val author = Author(0, books.author, books.country)

                myAuthorViewModel.addAuthor(author)
            }

        }
    }

}