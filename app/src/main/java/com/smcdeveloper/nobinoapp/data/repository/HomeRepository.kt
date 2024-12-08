package com.smcdeveloper.nobinoapp.data.repository

import com.smcdeveloper.nobinoapp.data.model.Category
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api:HomeApiInterface):BaseApiResponse2() {

    suspend fun getProducts(): NetworkResult<MovieResult> =

        safeApiCall {

            api.getMovies()


        }





    suspend fun getSlider(): NetworkResult<Slider> =

        safeApiCall {

            api.getSlider()


        }


    suspend fun getMoveListBySize(): NetworkResult<MovieResult> =

        safeApiCall {

            val category = Category.SERIES
            api.getMoviesBySize(size = "15", category = category.displayName)


        }

    suspend fun getMovieDetails(id: Int): NetworkResult<MovieResult> =
       safeApiCall {
           api.fetchMovieDetails(id)
       }
}





