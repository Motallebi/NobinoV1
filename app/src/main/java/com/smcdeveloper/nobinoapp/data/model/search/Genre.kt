package com.smcdeveloper.nobinoapp.data.model.search

import com.google.gson.annotations.SerializedName



data class Genre(
    @SerializedName("data")
    val genreInfo: List<GenreInfo>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long
)

