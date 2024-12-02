package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.ResponseResult
import com.smcdeveloper.nobinoapp.data.model.bb.MovieInfoData
import com.smcdeveloper.nobinoapp.data.model.bb.MovieResult
import com.smcdeveloper.nobinoapp.data.model.nn.Item
import com.smcdeveloper.nobinoapp.data.model.nn.MoviesData
import com.smcdeveloper.nobinoapp.data.model.product.product
import com.smcdeveloper.nobinoapp.data.model.testrest.PostResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface HomeApiInterface {

    @GET("api/products")
    suspend fun getMovies() : Response<MovieResult>










}