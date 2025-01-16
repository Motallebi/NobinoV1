package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.ApiResponse
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductCategories
import retrofit2.Response
import retrofit2.http.GET

interface ProductCategoriesApiInterface {

    @GET("api/settings/mainPage/categories")
    suspend fun getProductCategories() : Response<ProductCategories>


    @GET("api/settings/mainPage/categories")
    suspend fun getCategoriesResponse(): Response<ApiResponse<List<ProductCategories.ProductCategoryData>>>



}












