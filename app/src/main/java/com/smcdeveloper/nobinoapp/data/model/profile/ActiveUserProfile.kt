package com.smcdeveloper.nobinoapp.data.model.profile

import com.google.gson.annotations.SerializedName

data class ActiveUserProfile(

    @SerializedName("ageRange")
    val ageRange: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isMain")
    val isMain: Boolean,
    @SerializedName("name")
    val name: String







) {
}