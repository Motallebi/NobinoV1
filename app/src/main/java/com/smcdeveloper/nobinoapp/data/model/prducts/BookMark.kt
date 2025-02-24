package com.smcdeveloper.nobinoapp.data.model.prducts


import com.google.gson.annotations.SerializedName

data class BookMark(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("userMessage")
    val userMessage: String?
) {
    data class Data(
        @SerializedName("bookmarked")
        val bookmarked: Boolean?,
        @SerializedName("disliked")
        val disliked: Boolean?,
        @SerializedName("liked")
        val liked: Boolean?
    )
}