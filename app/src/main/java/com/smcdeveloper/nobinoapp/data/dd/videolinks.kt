package com.smcdeveloper.nobinoapp.data.dd

data class videolinks(
    val `data`: Data,
    val success: Boolean,
    val userMessage: String
) {
    data class Data(
        val actors: List<Actor>,
        val ages: String,
        val bookmarked: Boolean,
        val category: String,
        val countries: List<Country>,
        val createdAt: Long,
        val directors: List<Director>,
        val disliked: Boolean,
        val id: Int,
        val images: List<Image>,
        val imdbCode: String,
        val imdbRating: Double,
        val liked: Boolean,
        val longDescription: String,
        val name: String,
        val orderDate: Long,
        val owner: Owner,
        val productionYear: Int,
        val published: Boolean,
        val purchased: Boolean,
        val resolution: String,
        val screeningState: String,
        val shortDescription: String,
        val sounds: List<Any>,
        val state: String,
        val subscriptionType: String,
        val subtitles: List<Subtitle>,
        val tags: List<Tag>,
        val translatedName: String,
        val updatedAt: Long,
        val videoLink: String,
        val videos: List<Video>
    ) {
        data class Actor(
            val createdAt: Long,
            val description: String,
            val gender: String,
            val id: Int,
            val imagePath: String,
            val imdbCode: String,
            val instagramLink: String,
            val name: String,
            val translatedName: String
        )

        data class Country(
            val code: String,
            val createdAt: Long,
            val id: Int,
            val name: String,
            val updatedAt: Long
        )

        data class Director(
            val createdAt: Long,
            val description: String,
            val gender: String,
            val id: Int,
            val imagePath: String,
            val imdbCode: String,
            val instagramLink: String,
            val name: String,
            val translatedName: String,
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
            val translatedName: String,
            val updatedAt: Long
        )

        data class Subtitle(
            val createdAt: Long,
            val defaulted: Boolean,
            val id: Int,
            val language: String,
            val src: String
        )

        data class Tag(
            val description: String,
            val fixed: Boolean,
            val id: String,
            val invisible: Boolean,
            val name: String,
            val translatedName: String
        )

        data class Video(
            val createdAt: Long,
            val duration: Int,
            val id: Int,
            val resolution: String,
            val src: String,
            val type: String
        )
    }
}