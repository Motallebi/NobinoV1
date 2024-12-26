package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.Category
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.util.Constants.LOG_TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
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


    suspend fun getMoveListBySize(tag: String): NetworkResult<MovieResult> =

        safeApiCall {

            val category = Category.SERIES
            api.getMoviesByQueryParams(size = "15", category = category.displayName)


        }

    suspend fun getMovieDetails(id: Int): NetworkResult<MovieResult> =
        safeApiCall {
            api.fetchMovieDetails(id)
        }

    suspend fun getMoviesTag(id: Int): NetworkResult<MovieCat> =
        safeApiCall {
            api.fetchMovieTags(id)


        }

    //todo this function should complete

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchMovies(): Flow<NetworkResult<List<MovieResult.DataMovie.Item>>> = flow {
        emit(NetworkResult.Loading())

        // First API Call: Fetch Movie Categories
        val movieResult = safeApiCall { api.fetchMovieTags(10) }
        Log.d(LOG_TAG, "CategoryResult....")

        when (movieResult) {
            is NetworkResult.Loading -> {

            }

            is NetworkResult.Success -> {
                val tags = movieResult.data?.movieCatData?.tags?.filterNotNull()

                Log.d(LOG_TAG, "------TAGS-------" + tags?.get(0).toString())


                val firstTag = tags?.firstOrNull()

                // Log.d(LOG_TAG, "-----FirstTag-------$firstTag")


                if (!tags.isNullOrEmpty()) {

                    val tagMovieMap = tags.asFlow()
                        .flatMapConcat { tag ->
                            flow {

                                val movieResult = safeApiCall {

                                    api.getMovieTest(

                                        size = "10",
                                        category = "SERIES",

                                        tags = firstTag.toString()
                                    )


                                }


                                val movies = if (movieResult is NetworkResult.Success) {
                                    movieResult.data?.movieInfo?.items
                                        ?.filterNotNull()
                                        ?: emptyList()
                                } else emptyList()

                                emit(tag to movies)


                                //  movieResult.data?.movieInfo?.items?.firstOrNull()


                            }


                        }
                        .toList()
                        .toMap()


                    // Second API Call: Fetch Movies By Tag

                }
                Log.d(LOG_TAG, "-----movieResult-------" + movieResult.data.toString())


                /* when (movieResult) {
                        is NetworkResult.Loading->
                        {

                        }

                        is NetworkResult.Success -> {
                            emit(NetworkResult.Success(movieResult.data?.movieInfo?.items?.filterNotNull() ?: emptyList()))
                        }
                        is NetworkResult.Error -> emit(NetworkResult.Error(movieResult.message.toString()))
                    }*/

                //emit(NetworkResult.Success(movieResult.data?.?.items?.filterNotNull() ?: emptyList()))
            }

            is NetworkResult.Error -> emit(NetworkResult.Error(movieResult.message.toString()))


            /*else {
                    emit(NetworkResult.Error("No tags found"))
                }*/
        }
        //

    }

    fun fetchMoviesByCategory(tagId: Int): Flow<NetworkResult<List<MovieResult.DataMovie.Item>>> =
        flow {
            emit(NetworkResult.Loading())

            // First API Call: Fetch Category Tag
            val categoryResult = safeApiCall { api.fetchMovieTags(tagId) }



            when (categoryResult) {
                is NetworkResult.Success -> {
                    val firstTag =
                        categoryResult.data?.movieCatData?.tags?.filterNotNull()?.firstOrNull()

                    Log.d(LOG_TAG, "tag is$firstTag")


                    if (!firstTag.isNullOrEmpty()) {
                        // Second API Call: Fetch Movies By Tag
                        val movieResult = safeApiCall {
                            api.getMovieTest(

                                size = "4",
                                category = "SERIES",
                                tags = firstTag


                            )
                        }
                        when (movieResult) {
                            is NetworkResult.Success -> {
                                emit(
                                    NetworkResult.Success(
                                        movieResult.data?.movieInfo?.items?.filterNotNull()
                                            ?: emptyList()
                                    )
                                )
                            }

                            is NetworkResult.Error -> emit(NetworkResult.Error(movieResult.message.toString()))
                            is NetworkResult.Loading -> {}

                        }
                    } else {
                        emit(NetworkResult.Error("No valid tags found in the category response"))
                    }
                }

                is NetworkResult.Error -> emit(NetworkResult.Error(categoryResult.message.toString()))
                is NetworkResult.Loading -> {

                }


            }
        }



    // Fetch the tag by ID
    fun fetchTagById(tagId: Int): Flow<NetworkResult<MovieCat>> = flow {
        emit(NetworkResult.Loading())
        val result = safeApiCall { api.fetchMovieTags(tagId) }



        emit(
            when (result) {
                is NetworkResult.Success -> NetworkResult.Success(result.data)
                is NetworkResult.Error -> NetworkResult.Error(result.message.toString())
                is NetworkResult.Loading -> TODO()
            }
        )
    }


    // Fetch movies by tag
    fun fetchMoviesByTag(tag:String): Flow<NetworkResult<List<MovieResult.DataMovie.Item>>> = flow {
        emit(NetworkResult.Loading())
        val result = safeApiCall { api.getMovieTest(
            size = "10",
            category = "SERIES",
            tags = tag


           ) }
        Log.d(LOG_TAG,"fetchMoviesByTag${result.message.toString()}")


        emit(
            when (result) {
                is NetworkResult.Success -> {
                    NetworkResult.Success(result.data?.movieInfo?.items?.filterNotNull() ?: emptyList())



                }

                is NetworkResult.Error -> NetworkResult.Error(result.message.toString())
                is NetworkResult.Loading ->NetworkResult.Loading()
            }
        )
    }
}

























































