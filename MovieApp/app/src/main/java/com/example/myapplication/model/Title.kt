package com.example.myapplication.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Title(
    val id: Int,
    val title: String,
    val year: Int,
    val imdb_id: String,
    val tmdb_id: Int,
    val tmdb_type: String,
    val type: String
) : Parcelable