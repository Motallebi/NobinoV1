package com.smcdeveloper.nobinoapp.data.model.prducts

import com.google.gson.annotations.SerializedName

data class SpecialBanner(
    @SerializedName("data")
    val bannerData: List<BannerData>,
    val message: String,
    val status: Int,
    val success: Boolean,
    val timestamp: Long
) {
    data class BannerData(
        val id: Int,
        val imagePath: String,
        val product: Product,
        val type: String
    ) {
        data class Product(
            val ages: String,
            val category: String,
            val countries: List<Country>,
            val createdAt: Long,
            val id: Int,
            val images: List<Image>,
            val imdbCode: String,
            val longDescription: String,
            val name: String,
            val order: Int,
            val orderDate: Long,
            val owner: Owner,
            val parentId: Int,
            val productionYear: Int,
            val published: Boolean,
            val screeningState: String,
            val shortDescription: String,
            val state: String,
            val status: String,
            val subscriptionType: String,
            val tags: List<Tag>,
            val totalSell: Int,
            val translatedName: String,
            val updatedAt: Long
        ) {
            data class Country(
                val code: String,
                val createdAt: Long,
                val id: Int,
                val name: String,
                val updatedAt: Long
            )

            data class Image(
                val createdAt: Long,
                val id: Int,
                val imageType: String,
                val src: String
            )

            data class Owner(
                val createdAt: Long,
                val description: String,
                val id: Int,
                val name: String,
                val slug: String,
                val translatedName: String,
                val updatedAt: Long
            )

            data class Tag(
                val description: String,
                val fixed: Boolean,
                val id: String,
                val invisible: Boolean,
                val name: String,
                val translatedName: String
            )
        }
    }
}