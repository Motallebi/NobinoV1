package com.smcdeveloper.nobinoapp.data.model.favorit


import com.google.gson.annotations.SerializedName

data class Favorite(
    @SerializedName("data")
    val favoritData: FavoriteData?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("userMessage")
    val userMessage: String?
) {
    data class FavoriteData(
        @SerializedName("items")
        val items: List<Item?>?,
        @SerializedName("page")
        val page: Int?,
        @SerializedName("size")
        val size: Int?,
        @SerializedName("total")
        val total: Int?
    ) {
        data class Item(
            @SerializedName("ages")
            val ages: String?,
            @SerializedName("bookmarked")
            val bookmarked: Boolean?,
            @SerializedName("category")
            val category: String?,
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
            @SerializedName("order")
            val order: Int?,
            @SerializedName("orderDate")
            val orderDate: Long?,
            @SerializedName("parentId")
            val parentId: Int?,
            @SerializedName("popularityRate")
            val popularityRate: Int?,
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
            @SerializedName("status")
            val status: String?,
            @SerializedName("subscriptionType")
            val subscriptionType: String?,
            @SerializedName("totalSell")
            val totalSell: Int?,
            @SerializedName("translatedName")
            val translatedName: String?,
            @SerializedName("updatedAt")
            val updatedAt: Long?
        ) {
            data class Image(
                @SerializedName("createdAt")
                val createdAt: Long?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("imageType")
                val imageType: String?,
                @SerializedName("src")
                val src: String?,
                @SerializedName("updatedAt")
                val updatedAt: Long?
            )
        }
    }
}