package com.smcdeveloper.nobinoapp.data.model.prducts

data class ProductCategories(
    val categoryData: List<ProductCategoryData>,
    val message: String,
    val status: Int,
    val success: Boolean,
    val timestamp: Long
) {
    data class ProductCategoryData(
        val id: Int?,
        val image: String?,
        val name: String?,
        val tags: List<String> = emptyList(),
        val title: String?
    )
}