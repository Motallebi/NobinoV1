package com.smcdeveloper.nobinoapp.data.model.search


import com.google.gson.annotations.SerializedName

data class p2(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("total")
    val total: Int?
) {
    data class Data(
        @SerializedName("createdAt")
        val createdAt: Long?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("imagePath")
        val imagePath: String?,
        @SerializedName("imdbCode")
        val imdbCode: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("translatedName")
        val translatedName: String?,
        @SerializedName("updatedAt")
        val updatedAt: Long?
    )
}