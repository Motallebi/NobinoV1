package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApiInterface {

    @GET("api/products")
    suspend fun getMovies() : Response<MovieResult>


    @GET("api/products")
    suspend fun getMoviesByQueryParams(
        @Query("size") size: String="10",
        @Query("category") category: String="SERIES",
        @Query("tags") tags: String = "",
    ):Response<MovieResult>


    @GET("api/products")
    suspend fun getMovieTest(
        @Query("size") size: String="3",
        @Query("category") category: String="SERIES",
       @Query("tags") tags: String = "",
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



















