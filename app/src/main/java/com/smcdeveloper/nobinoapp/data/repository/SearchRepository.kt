package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.search.Countries
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.SearchApi
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import javax.inject.Inject

class SearchRepository @Inject constructor(val api:SearchApi) : BaseApiResponse2(){






    suspend fun fetchMovies(tag: String,categoty:String, size: Int,offset: Int,countries:String,name:String



    ): NetworkResult<MovieResult> {
        //val tagstring = tag.substring(1, tag.length - 1)
        val result = safeApiCall {


            api.getMoviesWithPages(tags = tag,
                category = categoty, size = size, offset = offset, countries = countries,
                name = name


            )


        }
        Log.d(NOBINO_LOG_TAG, "----fetchMovieTest-----${tag}")
        Log.d(NOBINO_LOG_TAG, "----fetchMovieTest-----")
        Log.d("fetchMovieTest", "----fetchMovieTest-----${tag}")

        return result


    }






    suspend fun getCountries():NetworkResult<Countries> =

        safeApiCall {

            api.fetchContries()


        }



















}