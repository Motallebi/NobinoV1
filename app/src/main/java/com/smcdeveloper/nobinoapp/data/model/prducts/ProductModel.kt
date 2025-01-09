package com.smcdeveloper.nobinoapp.data.model.prducts

data class ProductModel(
    val data: MovieInfo,
    val success: Boolean,
    val userMessage: String
) {
    data class MovieInfo(
        val actors: List<Actor>,
        val ages: String,
        val category: String,
        val countries: List<Country>,
        val createdAt: Long,
        val directors: List<Director>,
        val id: Int,
        val images: List<Image>,
        val imdbCode: String,
        val imdbRating: Double,
        val longDescription: String,
        val name: String,
        val orderDate: Long,
        val owner: Owner,
        val productionYear: Int,
        val published: Boolean,
        val screeningState: String,
        val shortDescription: String,
        val sounds: List<Any>,
        val state: String,
        val subscriptionType: String,
        val subtitles: List<Any>,
        val tags: List<Tag>,
        val translatedName: String,
        val updatedAt: Long,
        val videos: List<Any>
    ) {
        data class Actor(
            val createdAt: Long,
            val description: String,
            val gender: String,
            val id: Int,
            val imagePath: String,
            val imdbCode: String,
            val name: String,
            val translatedName: String,
            val updatedAt: Long
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
            val translatedName: String
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