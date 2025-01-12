package com.smcdeveloper.nobinoapp.data.model.profile

data class LoginResponse(

    val client_id: String,
    val grant_type: String,
    val client_secret: String,
    val mobile: String,
    val refNumber: String="",
    val otp: String="",
    val access_token:String=""





)
