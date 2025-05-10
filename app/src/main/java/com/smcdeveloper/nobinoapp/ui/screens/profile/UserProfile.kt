package com.smcdeveloper.nobinoapp.ui.screens.profile

import com.google.gson.annotations.SerializedName
import com.smcdeveloper.nobinoapp.data.model.profile.ActiveUserProfile

data class UserProfile(



@SerializedName("activeProfile")
val activeProfile: ActiveUserProfile,
@SerializedName("createdAt")
val createdAt: Long,
@SerializedName("firstName")
val firstName: String,
@SerializedName("lastName")
val lastName: String,
@SerializedName("email")
val email: String,
@SerializedName("birthDateLong")
val birthDate: Long,
@SerializedName("id")
val id: Int,
@SerializedName("mobile")
val mobile: String,
@SerializedName("status")
val status: String,
@SerializedName("username")
val username: String




)
