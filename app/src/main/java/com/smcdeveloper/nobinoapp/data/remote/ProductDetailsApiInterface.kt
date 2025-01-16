package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.ResponseResultWithList
import com.smcdeveloper.nobinoapp.data.model.ResponseResultWithSingleObject
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductDetailsApiInterface {

    @GET("api/products")
    suspend fun getMovies() : Response<MovieResult>



    @GET("api/products/{productId}")
    suspend fun getProductDetailInfo(

        @Path("productId") id: Int,
        @Header("Authorization") auth: String =""


    ):Response<ResponseResultWithSingleObject<ProductModel>>


    @GET("api/products/{productId}")
    suspend fun getRelatedProducts(

        @Path("productId") id: Int,
        @Query("tags") tags :String="",
        @Header("Authorization") auth: String =""


    ):Response<ResponseResultWithSingleObject<MovieResult>>










    @GET("api/products")
    suspend fun getMoviesByQueryParams(
        @Query("size") size: Int=10,
        @Query("category") category: String="SERIES",
        @Query("tags") tags: String = "",
        @Query("offset") offset: Int = 0,

    ):Response<MovieResult>


    @GET("api/products")
       suspend fun getMovieTest(
        @Query("size") size: String="20",
        @Query("category") category: String="",
       @Query("tags") tags: String = "",
        @Query("offset") offset: Int = 0,
    ):Response<MovieResult>








    @GET("api/settings/mainPage/sliders")
    suspend fun getSlider():Response<Slider>



    @GET("movies/{id}")
    suspend fun fetchMovieDetails(
        @Path("id") id: Int
    ): Response<MovieResult>

    @GET("api/settings/mainPage/queries/{id}")
    suspend fun fetchMovieTags(
        @Path("id") id: Int
    ): Response<MovieCat>








}



















