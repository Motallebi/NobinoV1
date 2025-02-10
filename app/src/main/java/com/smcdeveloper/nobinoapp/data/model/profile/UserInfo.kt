package com.smcdeveloper.nobinoapp.data.model.profile


import com.google.gson.annotations.SerializedName
import com.smcdeveloper.nobinoapp.ui.screens.profile.UserProfile


data class UserInfo(
    @SerializedName("data")
    val profileData: UserProfile,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("userMessage")
    val userMessage: String
)

{




}