package com.example.localbookfilter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.localbookfilter.data.Author.AuthorViewModel
import com.example.localbookfilter.data.Book.BookViewModel


class MainActivity : AppCompatActivity() {

    var author = ""

    private lateinit var myAuthorViewModel: AuthorViewModel
    private lateinit var myBookViewModel: BookViewModel

    private lateinit var httpApiService: HttpApiService
    private lateinit var myApp: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAuthorViewModel = ViewModelProvider(this).get(AuthorViewModel::class.java)
        myBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        myApp = application as MyApplication
        httpApiService = myApp.httpApiService

    }

    override fun onStart() {
        super.onStart()

        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        val firstTimeUse = sharedPref.getBoolean("first_time", true)

        if (firstTimeUse) {

            Toast.makeText(this, "Downloading data...", Toast.LENGTH_LONG).show()
            insertAuthorDataToDb()
            insertBookDataToDb()
            Thread.sleep(3000)

            Toast.makeText(this, "Download Complete!", Toast.LENGTH_LONG).show()
            val editor = sharedPref.edit()
            editor.putBoolean("first_time", false)
            editor.apply()

        }

        filterBooksDisplay()

    }

    private fun filterBooksDisplay() {
        val authorText = findViewById<TextInputEditText>(R.id.authorTextView)
        val filterButton = findViewById<Button>(R.id.filterButton)
        val resultCount = findViewById<TextView>(R.id.resultCountTextView)
        val result1 = findViewById<TextView>(R.id.resultTextView1)
        val result2 = findViewById<TextView>(R.id.resultTextView2)
        val result3 = findViewById<TextView>(R.id.resultTextView3)

        filterButton.setOnClickListener {

            author = authorText.text.toString()

            if (author.isNullOrEmpty()) {
                resultCount.text = "Please enter Author Name"
                result1.text = ""
                result2.text = ""
                result3.text = ""
            } else {
                myBookViewModel.readData(author)
                    .observe(this@MainActivity, Observer { books ->

                        var count = books.size

                        resultCount.text = "Results: $count"

                        if (count >= 3) {
                            val name1 = books.get(0).title
                            val id1 = books.get(0).book_id
                            result1.text = "Result: $name1 ($id1)"

                            val name2 = books.get(1).title
                            val id2 = books.get(1).book_id
                            result2.text = "Result: $name2 ($id2)"

                            val name3 = books.get(2).title
                            val id3 = books.get(2).book_id
                            result3.text = "Result: $name3 ($id3)"
                        }

                        if (count == 2) {
                            val name1 = books.get(0).title
                            val id1 = books.get(0).book_id
                            result1.text = "Result: $name1 ($id1)"

                            val name2 = books.get(1).title
                            val id2 = books.get(1).book_id
                            result2.text = "Result: $name2 ($id2)"

                            result3.text = ""
                        }

                        if (count == 1) {
                            val name1 = books.get(0).title
                            val id1 = books.get(0).book_id
                            result1.text = "Result: $name1 ($id1)"
                            result2.text = ""
                            result3.text = ""
                        }

                        if (count == 0) {
                            resultCount.text = "No books! found"
                            result1.text = ""
                            result2.text = ""
                            result3.text = ""
                        }

                    })

            }


        }


    }

    private fun insertBookDataToDb() {

        myBookViewModel.insertData(myAuthorViewModel, httpApiService)
    }

    private fun insertAuthorDataToDb() {

        myAuthorViewModel.insertData(myAuthorViewModel, httpApiService)
    }


}