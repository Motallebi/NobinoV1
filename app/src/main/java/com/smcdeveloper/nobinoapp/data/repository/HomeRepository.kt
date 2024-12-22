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


    suspend fun getMoveListBySize(tag:String): NetworkResult<MovieResult> =

        safeApiCall {

            val category = Category.SERIES
            api.getMoviesByQueryParams(size = "15", category = category.displayName)


        }

    suspend fun getMovieDetails(id: Int): NetworkResult<MovieResult> =
       safeApiCall {
           api.fetchMovieDetails(id)
       }

    suspend fun getMoviesTag(id:Int): NetworkResult<MovieCat> =
        safeApiCall {
            api.fetchMovieTags(id)


        }

    //todo this function should complete

   /* @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchMovies(): Flow<NetworkResult<List<MovieResult.DataMovie.Item>>> = flow {
        emit(NetworkResult.Loading())

        // First API Call: Fetch Movie Categories
        val movieResult = safeApiCall { api.fetchMovieTags(10) }
        Log.d(LOG_TAG,"CategoryResult....")

        when (movieResult) {
            is NetworkResult.Loading->
        {

            }

            is NetworkResult.Success -> {
                val tags = movieResult.data?.movieCatData?.tags?.filterNotNull()

                Log.d(LOG_TAG,"------TAGS-------"+tags?.get(0).toString())


                val firstTag = tags?.firstOrNull()

               // Log.d(LOG_TAG, "-----FirstTag-------$firstTag")


                if (!tags.isNullOrEmpty()) {

                    val tagMovieMap = tags.asFlow()
                        .flatMapConcat { tag->
                            flow {

                                val movieResult = safeApiCall {

                                    api.getMovieTest(

                                    size = "10",
                                    category ="SERIES" ,

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
                    Log.d(LOG_TAG,"-----movieResult-------"+movieResult.data.toString())




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
    }*/




























}





