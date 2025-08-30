package com.smcdeveloper.nobinoapp.data.model.advertise


import com.google.gson.annotations.SerializedName

data class Advertise(
    @SerializedName("data")
    val advertie: AdvertiseData?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("userMessage")
    val userMessage: String?
) {
    data class AdvertiseData(
        @SerializedName("fileUrl")
        val fileUrl: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("link")
        val link: String?,
        @SerializedName("minSkipTime")
        val minSkipTime: Int?,
        @SerializedName("skippable")
        val skippable: Boolean?,
        @SerializedName("title")
        val title: String?
    )
}