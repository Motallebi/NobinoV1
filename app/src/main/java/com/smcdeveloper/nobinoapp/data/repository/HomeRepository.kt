package com.smcdeveloper.nobinoapp.data.repository

import com.smcdeveloper.nobinoapp.data.model.Category
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


    suspend fun getSlider():NetworkResult =

        safeApiCall {

            api.getSlider()


        }







    suspend fun getMoveListBySize():NetworkResult =

        safeApiCall {

            val category=Category.SERIES
            api.getMoviesBySize(size = "15", category =category.displayName )


        }








}