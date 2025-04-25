package com.example.myapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.watchmode.com/v1/list-titles/?apiKey=aDBJLZnJZ6DLwIjL7wPFjJs0Y3qsvr41HkF9OIix&types=movie&sort_by=popularity_desc" // Replace with the base URL of your API

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}