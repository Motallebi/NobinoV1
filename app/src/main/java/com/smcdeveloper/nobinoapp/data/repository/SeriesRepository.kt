package com.smcdeveloper.nobinoapp.data.repository

import com.smcdeveloper.nobinoapp.data.model.Category
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.SeriesInterface
import javax.inject.Inject

class SeriesRepository @Inject constructor(private val api:SeriesInterface):BaseApiResponse2() {

    suspend fun getSeriesProductBysize(): NetworkResult<MovieResult> =

        safeApiCall {

            val category = Category.SERIES
            api.getSeriseBySize(size = "3", category = category.displayName)




        }

    suspend fun getSeriesDetails(id: Int): NetworkResult<MovieResult> =
        safeApiCall {
            api.fetchSeriesDetails(id)
        }












}





