package com.smcdeveloper.nobinoapp.data.model.avatar


import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("data")
    val avatarData: List<AvatarData?>?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("userMessage")
    val userMessage: String?
) {
    data class AvatarData(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("type")
        val type: String?
    )
}