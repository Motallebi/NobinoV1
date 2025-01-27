package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.Category
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
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
            api.getMoviesByQueryParams(size = 15, category = category.displayName)


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
        Log.d(NOBINO_LOG_TAG, "CategoryResult....")

        when (movieResult) {
            is NetworkResult.Loading -> {

            }

            is NetworkResult.Success -> {
                val tags = movieResult.data?.movieCatData?.tags?.filterNotNull()

                Log.d(NOBINO_LOG_TAG, "------TAGS-------" + tags?.get(0).toString())


                val firstTag = tags?.firstOrNull()

                // Log.d(LOG_TAG, "-----FirstTag-------$firstTag")


                if (!tags.isNullOrEmpty()) {

                    val tagMovieMap = tags.asFlow()
                        .flatMapConcat { tag ->
                            flow {

                                val movieResult = safeApiCall {

                                    api.getMoviesWithPages(

                                        size = 10,
                                        category = "",

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
                Log.d(NOBINO_LOG_TAG, "-----movieResult-------" + movieResult.data.toString())


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

    fun fetchMoviesByCategory(
        tagId: Int,
        size: Int = 10,
        offset: Int = 10
    )
            : Flow<NetworkResult<List<MovieResult.DataMovie.Item>>> =
        flow {
            emit(NetworkResult.Loading())

            // First API Call: Fetch Category Tag
            val categoryResult = safeApiCall { api.fetchMovieTags(tagId) }



            when (categoryResult) {
                is NetworkResult.Success -> {
                    val firstTag =
                        categoryResult.data?.movieCatData?.tags?.filterNotNull()?.firstOrNull()

                    Log.d(NOBINO_LOG_TAG, "tag is$firstTag")


                    if (!firstTag.isNullOrEmpty()) {
                        // Second API Call: Fetch Movies By Tag
                        val movieResult = safeApiCall {
                            api.getMoviesByQueryParams(

                                size = size,
                                category = "SERIES",
                                tags = firstTag,
                                offset = offset


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
        Log.d(NOBINO_LOG_TAG, "Starting fetch for tagId: $tagId")
        emit(NetworkResult.Loading())
        delay(1000) //

        val result = safeApiCall { api.fetchMovieTags(tagId) }
        Log.d(NOBINO_LOG_TAG, "API result for tagId: $tagId, Result: $result")
        Log.d(NOBINO_LOG_TAG, "fetchTagByID........  Tag is: ${tagId}")
        Log.d(NOBINO_LOG_TAG, "fetchTagByID........ Result is: ${result.message}")
        // var stat:NetworkResult<MovieCat>? = null


        emit(
            when (result) {
                is NetworkResult.Success -> {
                    Log.d(NOBINO_LOG_TAG, "Tag fetch success for tagId: $tagId, Data: ${result.data}")
                    NetworkResult.Success(result.data)


                }


                is NetworkResult.Error -> NetworkResult.Error(result.message.toString())
                is NetworkResult.Loading -> TODO()
            }
        )

        Log.d(NOBINO_LOG_TAG, "0000---0000" + result.message.toString())


    }


    // Fetch movies by tag
    fun fetchMoviesByTag(tag: String): Flow<NetworkResult<List<MovieResult.DataMovie.Item>>> =
        flow {
            Log.d(NOBINO_LOG_TAG, "Fetching movies for tag: $tag")

            emit(NetworkResult.Loading())
            delay(1000)
            val result = safeApiCall {
                api.getMoviesWithPages(
                    size = 10,
                    category = "",
                    tags = tag


                )
            }
            Log.d(NOBINO_LOG_TAG, "fetchMoviesByTag${result.message.toString()}")


            emit(
                when (result) {
                    is NetworkResult.Success -> {

                        NetworkResult.Success(
                            result.data?.movieInfo?.items?.filterNotNull() ?: emptyList()
                        )


                    }

                    is NetworkResult.Error -> NetworkResult.Error(result.message.toString())
                    is NetworkResult.Loading -> NetworkResult.Loading()
                }
            )
        }


    suspend fun fetchAllProductsByTag(
        size: Int,
        category: String,
        tags: String = "69f24e0b-b6c1-4094-88df-4db7476a2147",
        offset: Int


    ): NetworkResult<MovieResult> =

        safeApiCall {

            api.getMoviesByQueryParams(
                size = size,
                category = category,
                tags = "69f24e0b-b6c1-4094-88df-4db7476a2147",


                //tags =tags.substring(1, tags.length - 1) ,
                offset = 10


            )

        }


    suspend fun fetchMovieTest(tag: String,categoty:String, size: Int,offset: Int



    ): NetworkResult<MovieResult> {
        //val tagstring = tag.substring(1, tag.length - 1)
        val result = safeApiCall {


            api.getMoviesWithPages(tags = tag,
                category = categoty, size = size, offset = offset


            )


        }
        Log.d(NOBINO_LOG_TAG, "----fetchMovieTest-----${tag}")
        Log.d(NOBINO_LOG_TAG, "----fetchMovieTest-----")
        Log.d("fetchMovieTest", "----fetchMovieTest-----${tag}")

        return result


    }


    suspend fun fetchMovies(tag: String): NetworkResult<MovieResult> {
        // Simulate an API call, replace with your actual API logic

        val tagstring = tag.substring(1, tag.length - 1)
        val result = safeApiCall {


            api.getMoviesWithPages(tags = tagstring)


        }
        Log.d(NOBINO_LOG_TAG, "----fetchMovieTest-----${tag}")
        Log.d(NOBINO_LOG_TAG, "----fetchMovieTest-----")
        return result


    }




    suspend fun fetchAllMoviesResults(tags: List<String>): List<NetworkResult<MovieResult>> =
        coroutineScope {

            tags.map { tag ->
                async {
                    fetchMovies(tag) // Use your existing `fetchMovies` method
                }
            }.awaitAll() // Await all calls to complete


}


data class MovieParams(
    val size: String = "20",
    val category: String = "SERIES",
    val tags: String = "",
    val offset: Int = 0
)




    suspend fun fetchMoviesForTags(tagIds: List<Int>): List<NetworkResult<MovieResult>> = coroutineScope {
        val movieCatResults = tagIds.map { id ->
            async {
                val movieCatResult = getMoviesTag(id)

                val tag = (movieCatResult as? NetworkResult.Success)?.data?.movieCatData?.tags.toString()
                Log.d(NOBINO_LOG_TAG,"fetchMoviesForTags-----$tag")
                tag.let { fetchMoviesByTag2(it) }
            }
        }
        movieCatResults.awaitAll() // Wait for all tasks to complete
    }



   /* suspend fun fetchMovieDisplayData(tagIds: List<Int>): List<MovieDisplayData> = coroutineScope {
        Log.d(LOG_TAG, "fetchMovieDisplayData: Started with tagIds: $tagIds")
        tagIds.map { id ->
            async {
                Log.d(LOG_TAG, "Processing tag ID: $id")
                val movieCatResult = getMoviesTag(id)
                if (movieCatResult is NetworkResult.Success) {
                    Log.d(LOG_TAG, "fetchMovieDisplayData: Success for tag ID: $id")

                    val movieCatData = movieCatResult.data?.movieCatData
                    Log.d(LOG_TAG, "MovieCatData for tag ID $id: $movieCatData")
                    val tags = movieCatData?.tags ?: emptyList()
                    Log.d(LOG_TAG, "Tags for tag ID $id: $tags")

                    // Fetch all MovieResults for the tags
                    val movieResults = tags.mapNotNull { tag ->
                        Log.d(LOG_TAG, "Fetching movies for tag: $tag")

                        val result=fetchMoviesByTag2(tag ?: "")
                        when (result) {
                            is NetworkResult.Success ->
                            {
                               Log.d(LOG_TAG, "fetchMoviesByTag2: Success for tag: $tag")
                               Log.d(LOG_TAG, "fetchMoviesByTag2: Success Data IS: ${result.data?.movieInfo.toString()}")

                                result.data?.movieInfo?.items
                             // Log.d(LOG_TAG,"fetchMovieDisplayData success  second call")

                            }

                            else ->
                            {
                                Log.d(LOG_TAG, "fetchMoviesByTag2: Failed or loading for tag: $tag")
                                null // Skip on error or loading
                            }

                        }
                    }
                        .flatten() // Combine all results into a single list
                    movieResults.forEach {
                        Log.d(LOG_TAG, "MovieResult Item: ${it?.name}")

                    }

                    if (movieCatData != null) {
                        Log.d(LOG_TAG, "Creating MovieDisplayData for tag ID: $id")
                        MovieDisplayData(
                            movieCat = movieCatData,
                            movieResults = movieResults
                        )
                    } else {
                        Log.d(LOG_TAG, "MovieCatData is null for tag ID: $id")
                        null
                    }
                } else {
                    Log.d(LOG_TAG, "fetchMovieDisplayData: Failed for tag ID: $id")
                    null // Handle errors or skip
                }
            }
        }.awaitAll().filterNotNull() // Filter out null results
    }*/


    suspend fun fetchMovieDisplayData(tagIds: List<Int>): List<MovieDisplayData> = coroutineScope {
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
                    if (firstTag != null) {
                        val movieResult = fetchMoviesByTag2(firstTag)

                        if (movieResult is NetworkResult.Success) {
                            Log.d(NOBINO_LOG_TAG, "fetchMoviesByTag2: Success for tag: $firstTag")

                            val dataMovie = movieResult.data?.movieInfo
                            val movieItems = dataMovie?.items ?: emptyList()

                            movieItems.forEach {
                                Log.d(NOBINO_LOG_TAG, "Movie Item: ${it?.name}")
                            }

                            if (movieCatData != null && movieResult.data != null) {
                                MovieDisplayData(
                                    movieCat = movieCatData,
                                    movieResult = movieResult.data,
                                    movieItems = movieResult.data.movieInfo?.items ?: emptyList()

                                  //  movieItems = movieItems
                                )
                            } else {
                                null
                            }
                        } else {
                            Log.d(NOBINO_LOG_TAG, "fetchMoviesByTag2: Failed for tag: $firstTag")
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
























    suspend fun fetchMoviesByTag2(tag: String): NetworkResult<MovieResult> {
        val tagString = tag.substring(1, tag.length - 1) // Clean up tag format
        Log.d(NOBINO_LOG_TAG,"SAFE API CALL----"+tagString)


        val result: NetworkResult<MovieResult>

        result = safeApiCall { api.getMoviesWithPages(tags = tag)}

         result.data?.movieInfo?.items?.forEach {

             Log.d(NOBINO_LOG_TAG,"Movie Name is ${it?.name.toString()}")



         }




    //    Log.d(LOG_TAG,"SAFE API CALL-----${result.data?.movieInfo?.items.toString()}")


      //  return safeApiCall { api.getMovieTest(tags = tagString)
        return result
        }


















    private suspend fun fetchResultWithRetry(param: String):NetworkResult<MovieResult> {

        return retry(3) { fetchMovies(param) }
    }



    private suspend fun <T> retry(times: Int, block: suspend () -> T): T {
        repeat(times - 1) {
            try {
                return block()
            } catch (e: Exception) {
                // Log or handle the exception
            }
        }
        return block() // Final attempt
    }



}


































































