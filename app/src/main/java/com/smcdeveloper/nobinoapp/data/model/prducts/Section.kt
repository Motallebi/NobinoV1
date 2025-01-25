package com.smcdeveloper.nobinoapp.data.model.prducts

import com.google.gson.annotations.SerializedName

data class Section(
    @SerializedName("data")
    val sectionData: List<Data> = emptyList(),
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("timestamp")
    val timestamp: Long = 0L,
    @SerializedName("status")
    val status: Int = 0
) {
    data class Data(
        @SerializedName("count")
        val count: Int = 0,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("imagePath")
        val imagePath: String = "",
        @SerializedName("tags")
        val tags: List<String> = emptyList(),
        @SerializedName("title")
        val title: String = ""
    )
}






