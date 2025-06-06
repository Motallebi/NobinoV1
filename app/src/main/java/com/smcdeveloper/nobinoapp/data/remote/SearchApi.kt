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

    @GET("api/products")
    suspend fun getSearchMoviesWithPages(
        @Query("size") size: Int = 10,
        @Query("category") categoris :List<String>? = emptyList(),

        @Query("tags") tags: String?= null,
        @Query("offset") offset: Int = 0,
        @Query("countries") countries: String? = null,
        @Query("name") name: String? = null

    ): Response<MovieResult>














    @GET("/api/countries")
    suspend fun fetchContries(

    ): Response<Countries>
























}