package com.smcdeveloper.nobinoapp.data.model.search


import com.google.gson.annotations.SerializedName


data class Person(
    @SerializedName("data")
    val personInfo: List<PersonInfo>,
    @SerializedName("total")
    val total: Int
) {


}