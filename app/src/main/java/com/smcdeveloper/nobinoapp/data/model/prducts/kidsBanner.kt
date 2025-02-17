package com.smcdeveloper.nobinoapp.data.model.prducts


import com.google.gson.annotations.SerializedName

data class KidsBanner(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")

    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("timestamp")
    val timestamp: Long?
) {
    data class Data(
        @SerializedName("buttonLink")
        val buttonLink: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("leftImagePath")
        val leftImagePath: String?,
        @SerializedName("rightImagePath")
        val rightImagePath: String?
    )
}