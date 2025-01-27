package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.Section
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SeriesInterface {



    @GET("api/products")
    suspend fun getSeries(
        @Query("size") size: String= "10",
        @Query("category") category: String="",
        @Query("tags") tags: List<String> = emptyList()
    ): Response<MovieResult>


    @GET("api/products/{id}")
    suspend fun fetchSeriesDetails(
        @Path("id") id: Int
    ): Response<MovieResult>

    @GET("api/products/relatedProducts/{id}")
    suspend fun getSeriesEpisodes(
        @Path("id") id: Int) :Response<MovieResult>



    @GET("api/settings/customPage/queries/{id}")
    suspend fun getSectionNames(

        @Path("id") id: Int,


        ):Response<Section>








}