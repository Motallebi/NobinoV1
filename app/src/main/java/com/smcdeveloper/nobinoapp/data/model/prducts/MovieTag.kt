package com.smcdeveloper.nobinoapp.data.model.prducts


import com.google.gson.annotations.SerializedName

data class

MovieTag(
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
        @SerializedName("count")
        val count: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("tags")
        val tags: List<String?>?,
        @SerializedName("title")
        val title: String?
    )
}