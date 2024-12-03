package com.smcdeveloper.nobinoapp.data.model.nn

data class Director(
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