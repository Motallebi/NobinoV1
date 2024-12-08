package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiInterface {

    @GET("api/products")
    suspend fun getMovies() : Response<MovieResult>


    @GET("api/products")

    suspend fun getMoviesBySize(
        @Query("size") size: String,
        @Query("category") category: String
    ):Response<MovieResult>

    @GET("settings/mainPage/sliders")
    suspend fun getSlider():Response<Slider>

















}