package com.smcdeveloper.nobinoapp.data.model.search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("message")
    val message: String,
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("timestamp")
    val timestamp: Long
) {
    @Serializable
    data class Data(
        @SerialName("description")
        val description: String,
        @SerialName("fixed")
        val fixed: Boolean,
        @SerialName("id")
        val id: String,
        @SerialName("invisible")
        val invisible: Boolean,
        @SerialName("name")
        val name: String,
        @SerialName("slug")
        val slug: String,
        @SerialName("translatedName")
        val translatedName: String
    )
}