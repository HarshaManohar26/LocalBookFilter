package com.example.localbookfilter.data.Book

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.localbookfilter.data.Author.Author

@Entity(tableName = "book_data", indices = [Index(value = ["title"], unique = true)], foreignKeys = [
    ForeignKey(entity = Author::class,
        parentColumns = ["author_id"],
        childColumns = ["authorId"],
        onDelete = CASCADE)])
data class Book(
    @PrimaryKey(autoGenerate = true)
    val book_id: Int,
    val title: String,
    val image_link: String,
    val language: String,
    val link: String,
    val pages: Int,
    val year: Int,
    val authorId: Int
)


