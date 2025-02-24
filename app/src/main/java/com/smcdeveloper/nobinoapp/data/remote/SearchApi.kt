package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.plans.Plan
import com.smcdeveloper.nobinoapp.data.model.plans.Plans
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.search.Countries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SearchApi {





    @GET("api/products")
    suspend fun getMoviesWithPages(
        @Query("size") size: Int = 20,
        @Query("category") category: String = "",
        @Query("tags") tags: String = "",
        @Query("offset") offset: Int = 18,
        @Query("countries") countries: String = "",
        @Query("name") name: String = ""

    ): Response<MovieResult>




    @GET("/api/countries")
    suspend fun fetchContries(

    ): Response<Countries>
























}