package com.smcdeveloper.nobinoapp.data.model.search

import com.google.gson.annotations.SerializedName

data class PersonInfo(


@SerializedName("createdAt")
val createdAt: Long,
@SerializedName("description")
val description: String,
@SerializedName("gender")
val gender: String,
@SerializedName("id")
val id: Int,
@SerializedName("imagePath")
val imagePath: String,
@SerializedName("imdbCode")
val imdbCode: String,
@SerializedName("name")
val name: String,
@SerializedName("translatedName")
val translatedName: String,
@SerializedName("updatedAt")
val updatedAt: Long,
@SerializedName("instagramLink")
val instagramLink: String=""


)










