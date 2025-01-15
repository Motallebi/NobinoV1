package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductCategories
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductCategoriesApiInterface {

    @GET("api/settings/mainPage/categories")
    suspend fun getProductCategories() : Response<ProductCategories>


    @GET("api/settings/mainPage/categories")
    suspend fun getCategoriesResponse(): Response<ApiResponse<List<ProductCategories.ProductCategoryData>>>



}












