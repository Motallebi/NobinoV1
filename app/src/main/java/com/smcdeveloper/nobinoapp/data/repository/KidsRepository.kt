package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.Category
import com.smcdeveloper.nobinoapp.data.model.prducts.Delimiter
import com.smcdeveloper.nobinoapp.data.model.prducts.KidsBanner
import com.smcdeveloper.nobinoapp.data.model.prducts.KidsBannerPics
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.SpecialBanner
import com.smcdeveloper.nobinoapp.data.model.search.Countries
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import com.smcdeveloper.nobinoapp.util.MovieDisplayData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class KidsRepository @Inject constructor(private val api:HomeApiInterface):BaseApiResponse2() {


    suspend fun fetchMovieDisplayDataForkids(tagIds: List<Int>): List<MovieDisplayData> =
        coroutineScope {
            Log.d(NOBINO_LOG_TAG, "fetchMovieDisplayData: Started with tagIds: $tagIds")

            tagIds.map { id ->
                async {
                    Log.d(NOBINO_LOG_TAG, "Processing tag ID: $id")
                    val movieCatResult = getMoviesTag(id)

                    if (movieCatResult is NetworkResult.Success) {
                        Log.d(NOBINO_LOG_TAG, "fetchMovieDisplayData: Success for tag ID: $id")

                        val movieCatData = movieCatResult.data?.movieCatData
                        Log.d(NOBINO_LOG_TAG, "MovieCatData for tag ID $id: $movieCatData")

                        val tags = movieCatData?.tags ?: emptyList()
                        Log.d(NOBINO_LOG_TAG, "Tags for tag ID $id: $tags")

                        // Fetch the first tag's MovieResult
                        val firstTag = tags.firstOrNull()
                        if (tags != null) {
                            val movieResult = fetchMoviesByTagForkids(tags)

                            if (movieResult is NetworkResult.Success) {
                                Log.d(
                                    NOBINO_LOG_TAG,
                                    "fetchMoviesByTag2: Success for tag: $firstTag"
                                )

                                val dataMovie = movieResult.data?.movieInfo
                                val movieItems = dataMovie?.items ?: emptyList()

                                movieItems.forEach {
                                    Log.d(NOBINO_LOG_TAG, "Movie Item: ${it?.name}")
                                }

                                if (movieCatData != null && movieResult.data != null) {
                                    MovieDisplayData(
                                        movieCat = movieCatData,
                                        movieResult = movieResult.data,
                                        movieItems = movieResult.data.movieInfo?.items
                                            ?: emptyList()

                                        //  movieItems = movieItems
                                    )
                                } else {
                                    null
                                }
                            } else {
                                Log.d(
                                    NOBINO_LOG_TAG,
                                    "fetchMoviesByTag2: Failed for tag: $firstTag"
                                )
                                null
                            }
                        } else {
                            Log.d(NOBINO_LOG_TAG, "No valid tags found for tag ID: $id")
                            null
                        }
                    } else {
                        Log.d(NOBINO_LOG_TAG, "fetchMovieDisplayData: Failed for tag ID: $id")
                        null // Handle errors or skip
                    }
                }
            }.awaitAll().filterNotNull() // Filter out null results
        }


    suspend fun getMoviesTag(id: Int): NetworkResult<MovieCat> =
        safeApiCall {
            api.fetchMovieTags(id)


        }





    suspend fun fetchMoviesByTagForkids(tag: List<String?>): NetworkResult<MovieResult> {
        //  val tagString = tag.substring(1, tag.length - 1) // Clean up tag format
        //Log.d(NOBINO_LOG_TAG,"SAFE API CALL----"+tagString)


        val result: NetworkResult<MovieResult>

        result = safeApiCall { api.getMoviesForKids(tags = tag) }

        result.data?.movieInfo?.items?.forEach {

            Log.d(NOBINO_LOG_TAG, "Movie Name is ${it?.name.toString()}")


        }


        //    Log.d(LOG_TAG,"SAFE API CALL-----${result.data?.movieInfo?.items.toString()}")


        //  return safeApiCall { api.getMovieTest(tags = tagString)
        return result
    }



    suspend fun getSpecialBannerData():NetworkResult<SpecialBanner> =










        safeApiCall {



            try {
                Log.d("API_CALL", "Fetching SERIES from API...") // Log before calling API
                val response =   api.getSpecialBanner("SERIES")
                Log.d("API_CALL", "SERIES fetched successfully: ${response.body()}") // Log success response
                Log.d("API_CALL", "SERIES fetched successfully: ${  response.raw()}") // Log success response



                response
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error fetching delimiter: ${e.message}", e) // Log error
                throw e // Ensure the error is still handled
            }




















        }



    suspend fun getSlider(): NetworkResult<Slider> =

        safeApiCall {

            api.getSliderById("31")


        }




    suspend fun getKidsBanners(id:Int): NetworkResult<KidsBanner> =

        safeApiCall {

            try {
                Log.d("API_CALL", "Fetching delimiter from API...") // Log before calling API
                val response = api.getKidsBanners(id)


                Log.d("API_CALL", "Delimiter fetched successfully: ${response.body()}") // Log success response
                Log.d("API_CALL", "Delimiter fetched successfully: ${  response.raw()}") // Log success response



                response
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error fetching delimiter: ${e.message}", e) // Log error
                throw e // Ensure the error is still handled
            }


        }

    suspend fun getKidsBannerPics(): NetworkResult<KidsBannerPics> =

        safeApiCall {

            try {
                Log.d("API_CALL", "Fetching delimiter from API...") // Log before calling API
                val response = api.getKidsPics()


                Log.d("API_CALL", "Delimiter fetched successfully: ${response.body()}") // Log success response
                Log.d("API_CALL", "Delimiter fetched successfully: ${  response.raw()}") // Log success response



                response
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error fetching delimiter: ${e.message}", e) // Log error
                throw e // Ensure the error is still handled
            }


        }






    suspend fun fetchKidsBanners(id1: Int, id2: Int): NetworkResult<List<KidsBanner?>> = coroutineScope {
        val results = listOf(id1, id2).map { id ->
            async {
                runCatching {
                    Log.d("API_CALL", "Fetching kids banner for ID: $id")
                    val response = getKidsBanners(id)
                    Log.d("API_CALL", "Response for ID $id: $response")
                    response
                }.getOrElse {
                    Log.e("API_ERROR", "Failed to fetch kids banner for ID: $id", it)
                    NetworkResult.Error("Failed to fetch data for ID $id")
                }
            }
        }.awaitAll() // Wait for both calls to complete

        val successResults = results.filterIsInstance<NetworkResult.Success<KidsBanner>>() // Keep only successful results

        return@coroutineScope if (successResults.isNotEmpty()) {
            NetworkResult.Success(successResults.map { it.data }) // Merge successful responses
        }

        else {
            NetworkResult.Error("Both API calls failed")
        }
    }
















    suspend fun getRelatedEpisode(seriesId:Int):NetworkResult<MovieResult> =
        safeApiCall {

            api.getSeriesEpisodes(seriesId)




        }










}