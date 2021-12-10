package com.example.localbookfilter.data.Book

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBook(book: Book)

    @Query("SELECT * FROM book_data b LEFT JOIN author_data a ON b.authorId = a.author_id where a.author_name = :authorName")
    fun findAllData(authorName: String): LiveData<List<Book>>

}