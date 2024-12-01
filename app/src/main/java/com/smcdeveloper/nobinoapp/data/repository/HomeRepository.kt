package com.smcdeveloper.nobinoapp.data.repository

import com.smcdeveloper.nobinoapp.data.model.product.product
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse
import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api:HomeApiInterface):BaseApiResponse()
{

    suspend fun getProducts():NetworkResult<List<product>> =

        safeApiCall {

            api.getMovies()


        }






}