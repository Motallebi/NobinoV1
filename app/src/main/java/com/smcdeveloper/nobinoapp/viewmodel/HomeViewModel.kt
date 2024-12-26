package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import com.smcdeveloper.nobinoapp.util.Constants.LOG_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository):ViewModel() {

    private val _movies = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val movies: StateFlow<NetworkResult<MovieResult>> get() = _movies.asStateFlow()

    private val _moviesByTags = MutableStateFlow<NetworkResult<MovieCat>>(NetworkResult.Loading())
    val moviesByTags: StateFlow<NetworkResult<MovieCat>> get() = _moviesByTags.asStateFlow()

    private val _tags = MutableStateFlow<NetworkResult<MovieCat>>(NetworkResult.Loading())
    val Tags: StateFlow<NetworkResult<MovieCat>> get() = _tags.asStateFlow()


    private val _movieState = MutableStateFlow<NetworkResult<List<MovieResult.DataMovie.Item>>>(NetworkResult.Loading())
    val movieState: StateFlow<NetworkResult<List<MovieResult.DataMovie.Item>>> = _movieState

    private val _moviesByTag = MutableStateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>>(emptyMap())
    val moviesByTag: StateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>> = _moviesByTag

    private val _tagsState = MutableStateFlow<NetworkResult<List<Int>>>(NetworkResult.Loading())
    val tagsState: StateFlow<NetworkResult<List<Int>>> = _tagsState
   // val moviesByTag: StateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>> = _moviesByTag




    private val _movieDetails =
        MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val movieDetails: StateFlow<NetworkResult<MovieResult>> get() = _movieDetails.asStateFlow()


    val products = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val productsListBySize = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())


    private val _sliders = MutableStateFlow<NetworkResult<Slider>>(NetworkResult.Loading())
    val slider: StateFlow<NetworkResult<Slider>> get() = _sliders.asStateFlow()
    // val slider = MutableStateFlow<NetworkResult<Slider>>(NetworkResult.Loading())

    private val _moviesByParameter = MutableStateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>>(emptyMap())
    val moviesByParameter: StateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>> = _moviesByParameter

    private val _tagsAndMovies = MutableStateFlow<Map<MovieCat.MovieCatData, NetworkResult<List<MovieResult.DataMovie.Item>>>>(emptyMap())
    val tagsAndMovies: StateFlow<Map<MovieCat.MovieCatData, NetworkResult<List<MovieResult.DataMovie.Item>>>> = _tagsAndMovies


































    fun getProductBySize() {

        viewModelScope.launch {
            productsListBySize.emit(repository.getMoveListBySize(""))


        }
    }






























    // Fetch tag and its corresponding movies
    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchMoviesForTagGroup(tagId: Int) {
        // Fetch the tag first
        repository.fetchTagById(tagId)
            .flatMapConcat { tagResult ->
                if (tagResult is NetworkResult.Success) {

                    val tag = tagResult.data?.movieCatData?.title.toString()
                    val tag2=tagResult.data?.movieCatData?.tags?.get(0).toString()

                    Log.d(LOG_TAG,"fetchMoviesForTagGroup: ${tag}")
                    Log.d(LOG_TAG,"fetchMoviesForTagGroup: ${tag2}")

                    // Fetch movies for this tag
                    repository.fetchMoviesByTag(tag2).map { moviesResult ->
                        tag2 to moviesResult
                    }
                        .onEach { (tag, result) ->

                            if(!tag.isNullOrEmpty()) {

                                val movieCatData: MovieCat.MovieCatData = MovieCat.MovieCatData(
                                    title = tagResult.data?.movieCatData?.title.toString(), // Assuming `tite` is the field you want to populate with the tag name
                                    tags = listOf(tag2), // Example: adding the tag to a list of tags
                                    count = 0, // Populate with appropriate values if available
                                    id = tagResult.data?.movieCatData?.id // Populate with appropriate values if available
                                )







                                _tagsAndMovies.value = _tagsAndMovies.value + (movieCatData to result)
                            }
                        }









                }



                else {
                    flowOf(tagId.toString() to tagResult as NetworkResult<List<MovieResult.DataMovie.Item>>)
                }
            }

            .launchIn(viewModelScope)
    }
























    fun getProduct() {


        viewModelScope.launch {
            products.emit(repository.getProducts())


        }


    }


    fun fetchMovieDetails(id: Int) {
        viewModelScope.launch {
            _movieDetails.value = NetworkResult.Loading()
            val result = repository.getMovieDetails(id)
            _movieDetails.value = result
        }


    }


    fun getSlider() {

        viewModelScope.launch {
            _sliders.value = NetworkResult.Loading()
            val result = repository.getSlider()
            _sliders.value = result


        }


    }


    fun getMoviesByTags()
    {



        viewModelScope.launch {


                flow {
                    // First API call
                    val firstResponse = repository.getMoviesTag(1)
                    _tags.value=firstResponse
                    emit(firstResponse)
                }
                    .map { firstData ->
                        // Extract parameters from the first API response
                        val paramA = firstData.data?.movieCatData?.tags?.get(0).toString()
                        val title=firstData.data?.movieCatData?.title


                       // val paramB = firstData.paramB

                        // Second API call using extracted parameters
                        repository.getMoveListBySize(paramA)
                    }
                    .catch { error ->
                        Log.d(LOG_TAG,"We have Error.......")
                        // Handle any errors
                        _moviesByTags.value = NetworkResult.Loading()
                    }






                    }


















    }



//todo it should be completed
    fun fetchMovies(tagId:Int) {


        repository.fetchMoviesByCategory(tagId)
            .onEach {
                result ->

                    _movieState.value = result

                    Log.d(LOG_TAG,".......FETCH......")
                    Log.d(LOG_TAG,".......FETCH......"+_movieState.value.data.toString())



            }
            .launchIn(viewModelScope)
    }

    fun fetchMoviesByTag(tagId:Int) {
        _moviesByTag.value += (tagId to NetworkResult.Loading())

        repository.fetchMoviesByCategory(tagId)
            .onEach {
                    result ->

                _moviesByTag.value += (tagId to result)

                Log.d(LOG_TAG,".......FETCH......")
                Log.d(LOG_TAG,".......FETCH......"+_moviesByTag.value.toString())



            }
            .launchIn(viewModelScope)
    }


    fun fetchMoviesForTags() {
        (1..3).forEach { tag ->
            Log.d(LOG_TAG,"00000000000000000")
            fetchMoviesForTag(tag)
        }
    }
    private fun fetchMoviesForTag(tag: Int) {
        // Indicate loading for the current tag
        _moviesByTag.value += (tag to NetworkResult.Loading())

        repository.fetchMoviesByCategory(tag)
            .onEach { result ->
                // Update the map with the result for the tag
                _moviesByTag.value += (tag to result)
            }
            .launchIn(viewModelScope)
    }






  /*  fun fetchTags() {
        repository.fetchTags()
            .onEach { tagsResult ->
                _tagsState.value = tagsResult
            }
            .launchIn(viewModelScope)
    }*/




    fun fetchMoviesForParameters() {
        (1..10).forEach { param ->
            fetchMovies1(param)
        }
    }

    private fun fetchMovies1(param: Int) {


        _moviesByParameter.value += (param to NetworkResult.Loading())

        repository.fetchMoviesByCategory(param)
            .onEach { result ->
                // Update the map with the result for the parameter
                _moviesByParameter.value += (param to result)
            }
            .launchIn(viewModelScope)
    }


    // Fetch tag and its corresponding movies
    fun fetchMoviesForTagGroup1(tagId: Int) {
        repository.fetchTagById(tagId)
            .flatMapConcat { tagResult ->
                if (tagResult is NetworkResult.Success) {
                    val tag = MovieCat.MovieCatData(
                        title = tagResult.data?.movieCatData?.title,
                        tags = listOf(tagResult.data?.movieCatData?.title),
                        count = 0,
                        id = tagId
                    )
                    repository.fetchMoviesByTag(tagResult.data?.movieCatData?.tags?.get(0) ?: "").map { moviesResult ->
                        tag to moviesResult
                    }
                } else {
                    flowOf(
                        MovieCat.MovieCatData(
                            title = "Unknown",
                            tags = listOf(),
                            count = 0,
                            id = tagId
                        ) to tagResult as NetworkResult<List<MovieResult.DataMovie.Item>>
                    )
                }
            }
            .onEach { (tag, result) ->
                _tagsAndMovies.value = _tagsAndMovies.value + (tag to result)
            }
            .launchIn(viewModelScope)
    }




















}









