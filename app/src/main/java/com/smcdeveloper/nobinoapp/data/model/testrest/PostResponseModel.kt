package com.smcdeveloper.nobinoapp.data.model.testrest


import com.google.gson.annotations.SerializedName

data class PostResponseModel(
    @SerializedName("body")
    val body: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: Int?
)