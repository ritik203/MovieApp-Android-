package com.example.myapplication.repository

import com.example.myapplication.model.Title
import com.example.myapplication.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TitleRepository {
    suspend fun getTitles(): List<Title> = withContext(Dispatchers.IO) {
        RetrofitClient.apiService.getTitles().titles
    }
}