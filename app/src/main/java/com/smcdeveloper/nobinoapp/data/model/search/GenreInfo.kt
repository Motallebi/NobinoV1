package com.smcdeveloper.nobinoapp.data.model.search

import com.google.gson.annotations.SerializedName

data class GenreInfo(

    @SerializedName("description")
    val description: String,
    @SerializedName("fixed")
    var fixed: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("invisible")
    val invisible: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("translatedName")
    val translatedName: String



)
