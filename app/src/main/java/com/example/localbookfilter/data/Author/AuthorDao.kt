package com.example.localbookfilter.data.Author

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAuthor(author: Author)

    @Query("select author_id from author_data where author_name = :authName")
    fun getIdByName(authName: String): Int

}