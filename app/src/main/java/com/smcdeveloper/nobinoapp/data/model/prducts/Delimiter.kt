package com.smcdeveloper.nobinoapp.data.model.prducts

import com.google.gson.annotations.SerializedName

data class Delimiter(
    @SerializedName("data")
    val imageData: ImageData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    val success: Boolean,
    val timestamp: Long
) {
    data class ImageData(
        val id: Int,
        val leftImageLink: String,
        val leftImagePath: String,
        val rightImageLink: String,
        val rightImagePath: String
    )
}