package com.smcdeveloper.nobinoapp.data.model.prducts


import com.google.gson.annotations.SerializedName

data class KidsBannerPics(
    @SerializedName("data")
    val bannerData: List<BannerData?>?= emptyList(),
    @SerializedName("message")
    val message: String?="",
    @SerializedName("status")
    val status: Int?=0,
    @SerializedName("success")
    val success: Boolean?=false,
    @SerializedName("timestamp")
    val timestamp: Long?=0L
) {
    data class BannerData(
        @SerializedName("id")
        val id: Int?=0,
        @SerializedName("imagePath")
        val imagePath: String?="",
        @SerializedName("link")
        val link: String?="",
        @SerializedName("tags")
        val tags: List<Any?>?= emptyList(),
        @SerializedName("title")
        val title: String?=""
    )
}