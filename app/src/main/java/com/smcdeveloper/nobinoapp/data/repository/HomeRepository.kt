package com.smcdeveloper.nobinoapp.data.repository

import com.smcdeveloper.nobinoapp.data.model.bb.MovieInfoData
import com.smcdeveloper.nobinoapp.data.model.bb.MovieResult
import com.smcdeveloper.nobinoapp.data.model.nn.Item
import com.smcdeveloper.nobinoapp.data.model.nn.MoviesData
import com.smcdeveloper.nobinoapp.data.model.product.product
import com.smcdeveloper.nobinoapp.data.model.testrest.PostResponseModel
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse
import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api:HomeApiInterface):BaseApiResponse()
{

    suspend fun getProducts():NetworkResult =

        safeApiCall {

            api.getMovies()


        }






}