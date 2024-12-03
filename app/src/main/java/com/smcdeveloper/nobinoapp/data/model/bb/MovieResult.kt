package com.smcdeveloper.nobinoapp.data.model.bb


import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("data")
    val movieInfo: DataMovie?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("userMessage")
    val userMessage: String?
)

{
    data class DataMovie(
        @SerializedName("items")
        val items: List<Item?>?,
        @SerializedName("offset")
        val offset: Int?,
        @SerializedName("size")
        val size: Int?,
        @SerializedName("total")
        val total: Int?
    ) {
        data class Item(
            @SerializedName("actors")
            val actors: List<Actor?>?,
            @SerializedName("ages")
            val ages: String?,
            @SerializedName("category")
            val category: String?,
            @SerializedName("countries")
            val countries: List<Country?>?,
            @SerializedName("createdAt")
            val createdAt: Long?,
            @SerializedName("directors")
            val directors: List<Director?>?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("images")
            val images: List<Image?>?,
            @SerializedName("imdbCode")
            val imdbCode: String?,
            @SerializedName("longDescription")
            val longDescription: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("order")
            val order: Int?,
            @SerializedName("owner")
            val owner: Owner?,
            @SerializedName("parentId")
            val parentId: Int?,
            @SerializedName("planId")
            val planId: Int?,
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
            @SerializedName("sounds")
            val sounds: List<Sound?>?,
            @SerializedName("state")
            val state: String?,
            @SerializedName("status")
            val status: String?,
            @SerializedName("subscriptionType")
            val subscriptionType: String?,
            @SerializedName("subtitles")
            val subtitles: List<Subtitle?>?,
            @SerializedName("tags")
            val tags: List<Tag?>?,
            @SerializedName("translatedName")
            val translatedName: String?,
            @SerializedName("updatedAt")
            val updatedAt: Long?,
            @SerializedName("videos")
            val videos: List<Video?>?
        ) {
            data class Actor(
                @SerializedName("createdAt")
                val createdAt: Long?,
                @SerializedName("description")
                val description: String?,
                @SerializedName("gender")
                val gender: String?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("imagePath")
                val imagePath: String?,
                @SerializedName("imdbCode")
                val imdbCode: String?,
                @SerializedName("instagramLink")
                val instagramLink: String?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("translatedName")
                val translatedName: String?,
                @SerializedName("updatedAt")
                val updatedAt: Long?
            )

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

            data class Director(
                @SerializedName("createdAt")
                val createdAt: Long?,
                @SerializedName("description")
                val description: String?,
                @SerializedName("gender")
                val gender: String?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("imagePath")
                val imagePath: String?,
                @SerializedName("imdbCode")
                val imdbCode: String?,
                @SerializedName("instagramLink")
                val instagramLink: String?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("translatedName")
                val translatedName: String?,
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

            data class Sound(
                @SerializedName("createdAt")
                val createdAt: Long?,
                @SerializedName("defaulted")
                val defaulted: Boolean?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("language")
                val language: String?,
                @SerializedName("src")
                val src: String?,
                @SerializedName("updatedAt")
                val updatedAt: Long?
            )

            data class Subtitle(
                @SerializedName("createdAt")
                val createdAt: Long?,
                @SerializedName("defaulted")
                val defaulted: Boolean?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("language")
                val language: String?,
                @SerializedName("src")
                val src: String?,
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

            data class Video(
                @SerializedName("createdAt")
                val createdAt: Long?,
                @SerializedName("duration")
                val duration: Int?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("resolution")
                val resolution: String?,
                @SerializedName("src")
                val src: String?,
                @SerializedName("type")
                val type: String?
            )
        }
    }
}