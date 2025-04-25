package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Title
import com.example.myapplication.repository.TitleRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TitleViewModel(private val repository: TitleRepository) : ViewModel() {

    private val _titles = MutableLiveData<List<Title>>()
    val titles: LiveData<List<Title>> = _titles

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        Log.d("TitleViewModel", "ViewModel initialized")
        fetchTitles()
    }

    fun fetchTitles() {
        _loading.value = true
        viewModelScope.launch {
            try {
                Log.d("TitleViewModel", "Fetching titles...")
                val response = repository.getTitles()
                _titles.value = response
                _loading.value = false
                Log.d("TitleViewModel", "Titles fetched successfully: ${response.size} items")
                response.forEach { title ->
                    Log.v("TitleViewModel", "Title: ${title.title}, Year: ${title.year}")
                }
            } catch (e: HttpException) {
                _errorMessage.value = "HTTP Error: ${e.code()} ${e.message()}"
                _loading.value = false
                Log.e("TitleViewModel", "HTTP error fetching titles: ${e.message()}", e)
            } catch (e: IOException) {
                _errorMessage.value = "Network Error: ${e.message}"
                _loading.value = false
                Log.e("TitleViewModel", "Network error fetching titles: ${e.message}", e)
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
                _loading.value = false
                Log.e("TitleViewModel", "Error fetching titles: ${e.message}", e)
            }
        }
    }

    class TitleViewModelFactory(private val repository: TitleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TitleViewModel::class.java)) {
                return TitleViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}