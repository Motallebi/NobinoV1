package com.smcdeveloper.nobinoapp.data.model.prducts

data class se(
    val `data`: List<Data>,
    val message: String,
    val status: Int,
    val success: Boolean,
    val timestamp: Long
) {
    data class Data(
        val count: Int,
        val id: Int,
        val imagePath: String,
        val tags: List<String>,
        val title: String
    )
}