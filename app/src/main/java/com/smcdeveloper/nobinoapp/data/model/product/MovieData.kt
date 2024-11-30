package com.smcdeveloper.nobinoapp.data.model.product

data class MovieData(
    val actors: List<Actor>,
    val ages: String,
    val category: String,
    val countries: List<Country>,
    val createdAt: Long,
    val directors: List<Director>,
    val id: Int,
    val images: List<Any>,
    val imdbCode: String,
    val longDescription: String,
    val name: String,
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
)