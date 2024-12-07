package com.smcdeveloper.nobinoapp.data.model.bb


import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("data")
    val movieInfo: DataMovie?=null,
    @SerializedName("success")
    val success: Boolean?=false,
    @SerializedName("userMessage")
    val userMessage: String?=null







)


{
    data class DataMovie(
        @SerializedName("items")
        val items: List<Item?>?= emptyList(),
        @SerializedName("offset")
        val offset: Int?=0,
        @SerializedName("size")
        val size: Int?=0,
        @SerializedName("total")
        val total: Int?=0
    ) {
        data class Item(
            @SerializedName("actors")
            val actors: List<Actor?>?= emptyList(),
            @SerializedName("ages")
            val ages: String?=null,
            @SerializedName("category")
            val category: String?=null,
            @SerializedName("countries")
            val countries: List<Country?>?= emptyList(),
            @SerializedName("createdAt")
            val createdAt: Long?=0,
            @SerializedName("directors")
            val directors: List<Director?>?= emptyList(),
            @SerializedName("id")
            val id: Int?=0,
            @SerializedName("images")
            val images: List<Image?>?= emptyList(),
            @SerializedName("imdbCode")
            val imdbCode: String?=null,
            @SerializedName("longDescription")
            val longDescription: String?=null,
            @SerializedName("name")
            val name: String?=null,
            @SerializedName("order")
            val order: Int?=0,
            @SerializedName("owner")
            val owner: Owner?=null,
            @SerializedName("parentId")
            val parentId: Int?=0,
            @SerializedName("planId")
            val planId: Int?=0,
            @SerializedName("popularityRate")
            val popularityRate: Int?=0,
            @SerializedName("productionYear")
            val productionYear: Int?=0,
            @SerializedName("published")
            val published: Boolean?=null,
            @SerializedName("screeningState")
            val screeningState: String?=null,
            @SerializedName("shortDescription")
            val shortDescription: String?=null,
            @SerializedName("sounds")
            val sounds: List<Sound?>?= emptyList(),
            @SerializedName("state")
            val state: String?=null,
            @SerializedName("status")
            val status: String?=null,
            @SerializedName("subscriptionType")
            val subscriptionType: String?=null,
            @SerializedName("subtitles")
            val subtitles: List<Subtitle?>?= emptyList(),
            @SerializedName("tags")
            val tags: List<Tag?>?= emptyList(),
            @SerializedName("translatedName")
            val translatedName: String?=null,
            @SerializedName("updatedAt")
            val updatedAt: Long?=0,
            @SerializedName("videos")
            val videos: List<Video?>?=null
        ) {
            data class Actor(
                @SerializedName("createdAt")
                val createdAt: Long?=0,
                @SerializedName("description")
                val description: String?=null,
                @SerializedName("gender")
                val gender: String?,
                @SerializedName("id")
                val id: Int?=0,
                @SerializedName("imagePath")
                val imagePath: String?,
                @SerializedName("imdbCode")
                val imdbCode: String?=null,
                @SerializedName("instagramLink")
                val instagramLink: String?=null,
                @SerializedName("name")
                val name: String?=null,
                @SerializedName("translatedName")
                val translatedName: String?=null,
                @SerializedName("updatedAt")
                val updatedAt: Long?=0
            )

            data class Country(
                @SerializedName("code")
                val code: String?=null,
                @SerializedName("createdAt")
                val createdAt: Long?=0,
                @SerializedName("id")
                val id: Int?=0,
                @SerializedName("name")
                val name: String?,
                @SerializedName("updatedAt")
                val updatedAt: Long?
            )

            data class Director(
                @SerializedName("createdAt")
                val createdAt: Long?=0,
                @SerializedName("description")
                val description: String?=null,
                @SerializedName("gender")
                val gender: String?=null,
                @SerializedName("id")
                val id: Int?=0,
                @SerializedName("imagePath")
                val imagePath: String?=null,
                @SerializedName("imdbCode")
                val imdbCode: String?=null,
                @SerializedName("instagramLink")
                val instagramLink: String?=null,
                @SerializedName("name")
                val name: String?=null,
                @SerializedName("translatedName")
                val translatedName: String?=null,
                @SerializedName("updatedAt")
                val updatedAt: Long?=0
            )

            data class Image(
                @SerializedName("createdAt")
                val createdAt: Long?=0,
                @SerializedName("id")
                val id: Int?=0,
                @SerializedName("imageType")
                val imageType: String?=null,
                @SerializedName("src")
                val src: String?=null
            )

            data class Owner(
                @SerializedName("createdAt")
                val createdAt: Long?=0,
                @SerializedName("description")
                val description: String?=null,
                @SerializedName("id")
                val id: Int?=0,
                @SerializedName("name")
                val name: String?=null,
                @SerializedName("slug")
                val slug: String?=null,
                @SerializedName("translatedName")
                val translatedName: String?=null,
                @SerializedName("updatedAt")
                val updatedAt: Long?=0
            )

            data class Sound(
                @SerializedName("createdAt")
                val createdAt: Long?=0,
                @SerializedName("defaulted")
                val defaulted: Boolean?=null,
                @SerializedName("id")
                val id: Int?=0,
                @SerializedName("language")
                val language: String?=null,
                @SerializedName("src")
                val src: String?=null,
                @SerializedName("updatedAt")
                val updatedAt: Long?=0
            )

            data class Subtitle(
                @SerializedName("createdAt")
                val createdAt: Long?=0,
                @SerializedName("defaulted")
                val defaulted: Boolean?=null,
                @SerializedName("id")
                val id: Int?=0,
                @SerializedName("language")
                val language: String?=null,
                @SerializedName("src")
                val src: String?=null,
                @SerializedName("updatedAt")
                val updatedAt: Long?=0
            )

            data class Tag(
                @SerializedName("description")
                val description: String?=null,
                @SerializedName("fixed")
                val fixed: Boolean?=null,
                @SerializedName("id")
                val id: String?=null,
                @SerializedName("invisible")
                val invisible: Boolean?=null,
                @SerializedName("name")
                val name: String?=null,
                @SerializedName("translatedName")
                val translatedName: String?=null
            )

            data class Video(
                @SerializedName("createdAt")
                val createdAt: Long?=null,
                @SerializedName("duration")
                val duration: Int?=0,
                @SerializedName("id")
                val id: Int?=0,
                @SerializedName("resolution")
                val resolution: String?=null,
                @SerializedName("src")
                val src: String?=null,
                @SerializedName("type")
                val type: String?=null
            )
        }
    }
}