package com.smcdeveloper.nobinoapp.data.model.product

data class Actor(
    val createdAt: Long,
    val description: String,
    val gender: String,
    val id: Int,
    val imagePath: String,
    val imdbCode: String,
    val name: String,
    val translatedName: String,
    val updatedAt: Long
)