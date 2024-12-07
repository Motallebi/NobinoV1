package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.ResponseResult
import com.smcdeveloper.nobinoapp.data.model.bb.MovieInfoData
import com.smcdeveloper.nobinoapp.data.model.bb.MovieResult
import com.smcdeveloper.nobinoapp.data.model.nn.Item
import com.smcdeveloper.nobinoapp.data.model.nn.MoviesData
import com.smcdeveloper.nobinoapp.data.model.product.product
import com.smcdeveloper.nobinoapp.data.model.testrest.PostResponseModel
import com.smcdeveloper.nobinoapp.util.RequestMovoeBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
















}