package com.example.myapplication.network


import com.example.myapplication.model.TitlesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("https://api.watchmode.com/v1/list-titles/?apiKey=aDBJLZnJZ6DLwIjL7wPFjJs0Y3qsvr41HkF9OIix&types=movie&sort_by=popularity_desc") // Replace with the actual API endpoint
    suspend fun getTitles(): TitlesResponse
}