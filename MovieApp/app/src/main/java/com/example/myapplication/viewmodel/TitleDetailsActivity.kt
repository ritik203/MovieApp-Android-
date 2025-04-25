package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityTitleDetailsBinding
import com.example.myapplication.model.Title

class TitleDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTitleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTitleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getParcelableExtra<Title>("title") // Use the key "title"
        Log.d("TitleDetailsActivity", "onCreate() called with title: $title")

        if (title != null) {
            displayTitleDetails(title)
        } else {
            // Handle the error, maybe finish the activity and show a Toast
            Log.e("TitleDetailsActivity", "Title is null")
            finish() // Close this activity
        }
    }

    private fun displayTitleDetails(title: Title) {
        binding.titleTextView.text = title.title
        binding.yearTextView.text = "Year: ${title.year}"
        binding.imdbIdTextView.text = "IMDb ID: ${title.imdb_id}"
        binding.tmdbIdTextView.text = "TMDB ID: ${title.tmdb_id}"
        binding.tmdbTypeTextView.text = "TMDB Type: ${title.tmdb_type}"
        binding.typeTextView.text = "Type: ${title.type}"
        // You can add more views to your layout to display all the details
    }
}