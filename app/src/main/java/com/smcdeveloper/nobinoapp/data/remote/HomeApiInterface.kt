package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.ResponseResult
import com.smcdeveloper.nobinoapp.data.model.product.product
import retrofit2.Response
import retrofit2.http.GET

interface HomeApiInterface {

    @GET("api/products")
    suspend fun getMovies() : Response<ResponseResult<List<product>>>










}