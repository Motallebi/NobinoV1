package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.Category
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.Section
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.SeriesInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

import javax.inject.Inject

class SeriesRepository @Inject constructor(private val api:SeriesInterface):BaseApiResponse2() {

    suspend fun getSeriesProductBysize(): NetworkResult<MovieResult> =

        safeApiCall {

            val category = Category.SERIES
            api.getSeries(size = "3", category = category.displayName)


        }

    suspend fun getSeriesDetails(id: Int): NetworkResult<MovieResult> =
        safeApiCall {
            api.fetchSeriesDetails(id)
        }


    suspend fun getSeriesSectionNames(id: Int) {
        val result = safeApiCall {
            api.getSectionNames(id)

        }


    }


    suspend fun fetchMoviesByTag2(tags: List<String>): NetworkResult<MovieResult> {

        val result = safeApiCall {
            api.getSeries(tags = tags)
        }
        return result


    }





    suspend fun getSlider(sliderId:String): NetworkResult<Slider> =

        safeApiCall {

            api.getSliderById(sliderId)


        }












    fun fetchSectionNames(id: Int): Flow<NetworkResult<List<Section.Data>>> = flow {
        Log.d("FETCH_SECTIONS", "Fetching sections for ID: $id") // Log the start of the process
        emit(NetworkResult.Loading()) // Emit loading state

        val result = safeApiCall {
            Log.d("FETCH_SECTIONS", "Making API call to fetch sections for ID: $id")
            api.getSectionNames(id) // API call
        }

        if (result is NetworkResult.Success) {
            val sectionData = result.data?.sectionData.orEmpty() // Extract sectionData list


            Log.d("FETCH_SECTIONS", "section data" + result.data?.sectionData.toString())
            Log.d("FETCH_SECTIONS", "section data" + result.data?.toString())


            Log.d(
                "FETCH_SECTIONS",
                "Successfully fetched sections: ${sectionData.size} sections retrieved"
            )
            emit(NetworkResult.Success(sectionData)) // Emit success
        } else if (result is NetworkResult.Error) {
            Log.e("FETCH_SECTIONS", "Error fetching sections: ${result.message}")
            emit(NetworkResult.Error(result.message ?: "Failed to fetch sections")) // Emit error
        }
    }.onStart {
        Log.d("FETCH_SECTIONS", "Flow started for fetching sections")
    }.onCompletion {
        Log.d("FETCH_SECTIONS", "Flow completed for fetching sections")
    }














    fun fetchMoviesByTag(tags: List<String>): Flow<NetworkResult<MovieResult>> = flow {
        Log.d("FETCH_MOVIES_TAG", "Fetching movies for tags: $tags")
        emit(NetworkResult.Loading()) // Emit loading state

        val result = safeApiCall {
            Log.d("FETCH_MOVIES_TAG", "Making API call with tags: $tags")
            val tag1="32dcb9a6-0b99-42c1-a2a0-f2dd20dbc7fa"
            val tags3= listOf("30980d7f-1a0c-4d23-a465-2a9c59b8bd6b",
            "8b6b91ff-046b-40f6-b834-e5bbe175ec65")

            api.getSeries(tags =tags)
        }

        when (result) {
            is NetworkResult.Success -> {

                val movieResult = result.data
                Log.d("FETCH_MOVIES_TAG","number of results"+movieResult?.movieInfo?.size.toString())
                Log.d("FETCH_MOVIES_TAG","number of results"+movieResult?.movieInfo?.toString())
                if (movieResult != null) {
                    Log.d("FETCH_MOVIES_TAG", "Movies fetched successfully. MovieResult: $movieResult")
                    emit(NetworkResult.Success((movieResult)))
                } else {
                    Log.e("FETCH_MOVIES_TAG", "MovieResult is null")
                    emit(NetworkResult.Error("No movie results found"))
                }
            }
            is NetworkResult.Error -> {
                Log.e("FETCH_MOVIES_TAG", "Error fetching movies: ${result.message}")
                emit(NetworkResult.Error(result.message ?: "Error fetching movies"))
            }

            is NetworkResult.Loading -> TODO()
        }
    }.onStart {
        Log.d("FETCH_MOVIES_TAG", "Flow started")
    }.onCompletion {
        Log.d("FETCH_MOVIES_TAG", "Flow completed")
    }




















    fun fetchMoviesByTag3(tags: List<String>): Flow<NetworkResult<List<MovieResult.DataMovie.Item>>> = flow {
        Log.d("FETCH_MOVIES1", "fetchMoviesByTag called: $tags") // Log the start of the process
        Log.d("FETCH_MOVIES1", "Fetching movies for tags: $tags") // Log the start of the process
        emit(NetworkResult.Loading()) // Emit loading state
        Log.d("FETCH_MOVIES2", "Loading state emitted")

        val result = safeApiCall {
            Log.d("FETCH_MOVIES1", "Making API call with tags: $tags") // Log before API call
            api.getSeries(tags = tags) // API call
        }

        when (result) {
            is NetworkResult.Success -> {
                Log.d("API_SUCCESS", "Raw response: ${result.data}") // Log the raw response
                val items = result.data?.movieInfo?.items.orEmpty().filterNotNull()
                Log.d("API_SUCCESS", "Parsed items: ${items.size} movies retrieved") // Log the parsed items
                emit(NetworkResult.Success(items)) // Emit success
            }

            is NetworkResult.Error -> {
                Log.e("API_ERROR", "Error fetching movies: ${result.message}") // Log the error message
                emit(NetworkResult.Error(result.message ?: "Failed to fetch movies")) // Emit error
            }

            else -> {
                Log.e("API_ERROR", "Unexpected result type: $result")
            }
        } as Unit
    }.onStart {
        Log.d("FETCH_MOVIES", "Flow started for fetching movies") // Log flow start
    }.onCompletion {
        Log.d("FETCH_MOVIES", "Flow completed for fetching movies") // Log flow completion
    }

















}













































































