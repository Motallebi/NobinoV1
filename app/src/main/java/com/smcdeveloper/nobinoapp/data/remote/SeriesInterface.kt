package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SeriesInterface {


    @GET("api/products")

    suspend fun getSeriseBySize(
        @Query("size") size: String,
        @Query("category") category: String
    ): Response<MovieResult>


    @GET("products/{id}")
    suspend fun fetchSeriesDetails(
        @Path("id") id: Int
    ): Response<MovieResult>

    @GET("products/relatedProducts/{id}")
    suspend fun getSeriesEpisodes(
        @Path("id") id: Int) :Response<MovieResult>








}