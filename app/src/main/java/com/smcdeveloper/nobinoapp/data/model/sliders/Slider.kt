package com.smcdeveloper.nobinoapp.data.model.sliders


import com.google.gson.annotations.SerializedName

data class Slider(
    @SerializedName("data")
    val `data`: List<Sliderinfo?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("timestamp")
    val timestamp: Long?
) {
    data class Sliderinfo(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("imageHorizontalPath")
        val imageHorizontalPath: String?,
        @SerializedName("imageVerticalPath")
        val imageVerticalPath: String?,
        @SerializedName("product")
        val product: Product?,
        @SerializedName("sort")
        val sort: Int?
    ) {
        data class Product(
            @SerializedName("ages")
            val ages: String?,
            @SerializedName("category")
            val category: String?,
            @SerializedName("countries")
            val countries: List<Country?>?,
            @SerializedName("createdAt")
            val createdAt: Long?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("images")
            val images: List<Image?>?,
            @SerializedName("imdbCode")
            val imdbCode: String?,
            @SerializedName("imdbLastUpdateTime")
            val imdbLastUpdateTime: Long?,
            @SerializedName("imdbRating")
            val imdbRating: Double?,
            @SerializedName("longDescription")
            val longDescription: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("owner")
            val owner: Owner?,
            @SerializedName("productionYear")
            val productionYear: Int?,
            @SerializedName("published")
            val published: Boolean?,
            @SerializedName("screeningState")
            val screeningState: String?,
            @SerializedName("shortDescription")
            val shortDescription: String?,
            @SerializedName("state")
            val state: String?,
            @SerializedName("subscriptionType")
            val subscriptionType: String?,
            @SerializedName("tags")
            val tags: List<Tag?>?,
            @SerializedName("totalSell")
            val totalSell: Int?,
            @SerializedName("translatedName")
            val translatedName: String?,
            @SerializedName("updatedAt")
            val updatedAt: Long?
        ) {
            data class Country(
                @SerializedName("code")
                val code: String?,
                @SerializedName("createdAt")
                val createdAt: Long?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("updatedAt")
                val updatedAt: Long?
            )

            data class Image(
                @SerializedName("createdAt")
                val createdAt: Long?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("imageType")
                val imageType: String?,
                @SerializedName("src")
                val src: String?
            )

            data class Owner(
                @SerializedName("createdAt")
                val createdAt: Long?,
                @SerializedName("description")
                val description: String?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("slug")
                val slug: String?,
                @SerializedName("translatedName")
                val translatedName: String?,
                @SerializedName("updatedAt")
                val updatedAt: Long?
            )

            data class Tag(
                @SerializedName("description")
                val description: String?,
                @SerializedName("fixed")
                val fixed: Boolean?,
                @SerializedName("id")
                val id: String?,
                @SerializedName("invisible")
                val invisible: Boolean?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("translatedName")
                val translatedName: String?
            )
        }
    }
}