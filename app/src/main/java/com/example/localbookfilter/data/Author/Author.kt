package com.example.localbookfilter.data.Author

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "author_data", indices = [Index(value = ["author_name"], unique = true)])
data class Author(
    @PrimaryKey(autoGenerate = true)
    val author_id: Int,
    val author_name: String,
    val country: String
)
