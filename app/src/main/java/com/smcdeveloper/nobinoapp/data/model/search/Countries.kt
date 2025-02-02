package com.smcdeveloper.nobinoapp.data.model.search

import com.google.gson.annotations.SerializedName

data class Countries(
    @SerializedName("data")
    val countryInfo: List<CountryInfo>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long
)
{

}