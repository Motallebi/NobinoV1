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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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


    private val _movieDetails =
        MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val movieDetails: StateFlow<NetworkResult<MovieResult>> get() = _movieDetails.asStateFlow()


    val products = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val productsListBySize = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())


    private val _sliders = MutableStateFlow<NetworkResult<Slider>>(NetworkResult.Loading())
    val slider: StateFlow<NetworkResult<Slider>> get() = _sliders.asStateFlow()
    // val slider = MutableStateFlow<NetworkResult<Slider>>(NetworkResult.Loading())

























    fun getProductBySize() {

        viewModelScope.launch {
            productsListBySize.emit(repository.getMoveListBySize(""))


        }
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
   /* fun fetchMovies() {


        repository.fetchMovies()
            .onEach {
                result ->

                    _movieState.value = result

                    Log.d(LOG_TAG,".......FETCH......")


            }
            .launchIn(viewModelScope)
    }*/






}






