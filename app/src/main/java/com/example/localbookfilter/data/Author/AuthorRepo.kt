package com.example.localbookfilter.data.Author

import androidx.lifecycle.LiveData

class AuthorRepo(private val authorDao: AuthorDao) {

    suspend fun addAuthor(author: Author){
        authorDao.addAuthor(author)
    }

    fun getIdByName(authName: String): Int{
        return authorDao.getIdByName(authName)
    }

}