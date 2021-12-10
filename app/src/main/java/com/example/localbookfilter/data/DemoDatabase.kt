package com.example.localbookfilter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.localbookfilter.data.Author.Author
import com.example.localbookfilter.data.Author.AuthorDao
import com.example.localbookfilter.data.Book.Book
import com.example.localbookfilter.data.Book.BookDao

@Database(entities = [Book::class, Author::class], version = 1, exportSchema = false)
abstract class DemoDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun authorDao(): AuthorDao

    companion object {
        @Volatile
        private var INSTANCE: DemoDatabase? = null

        fun getDb(context: Context): DemoDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DemoDatabase::class.java,
                    "demo_db"
                ).build()

                INSTANCE = instance
                return instance

            }
        }
    }

}