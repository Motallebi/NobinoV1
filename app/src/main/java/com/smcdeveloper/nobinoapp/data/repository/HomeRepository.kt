package com.smcdeveloper.nobinoapp.data.repository

import com.smcdeveloper.nobinoapp.data.model.Category
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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


    suspend fun getMoveListBySize(tag:String): NetworkResult<MovieResult> =

        safeApiCall {

            val category = Category.SERIES
            api.getMoviesByQueryParams(size = "15", category = category.displayName,tag)


        }

    suspend fun getMovieDetails(id: Int): NetworkResult<MovieResult> =
       safeApiCall {
           api.fetchMovieDetails(id)
       }

    suspend fun getMoviesTag(id:Int): NetworkResult<MovieCat> =
        safeApiCall {
            api.fetchMovieTags(id)


        }


    fun fetchMovies(): Flow<NetworkResult<List<MovieResult.DataMovie.Item>>> = flow {
        emit(NetworkResult.Loading())

        // First API Call: Fetch Movie Categories
        val categoryResult = safeApiCall { api.fetchMovieTags(1) }

        when (categoryResult) {
            is NetworkResult.Loading->
        {

            }

            is NetworkResult.Success -> {
                val tags = categoryResult.data?.movieCatData?.tags?.filterNotNull()
                val firstTag = tags?.firstOrNull()

                if (!firstTag.isNullOrEmpty()) {
                    // Second API Call: Fetch Movies By Tag
                    val movieResult = safeApiCall { api.getMoviesByQueryParams(firstTag) }
                    when (movieResult) {
                        is NetworkResult.Loading->
                        {

                        }

                        is NetworkResult.Success -> {
                            emit(NetworkResult.Success(movieResult.data?.movieInfo?.items?.filterNotNull() ?: emptyList()))
                        }
                        is NetworkResult.Error -> emit(NetworkResult.Error(movieResult.message.toString()))
                    }
                } else {
                    emit(NetworkResult.Error("No tags found"))
                }
            }
            is NetworkResult.Error -> emit(NetworkResult.Error(categoryResult.message.toString()))
        }
    }




























}





