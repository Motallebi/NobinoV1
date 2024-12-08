package com.smcdeveloper.nobinoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository):ViewModel() {

    private val _movies = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val movies: StateFlow<NetworkResult<MovieResult>> get() = _movies.asStateFlow()

    private val _movieDetails =
        MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
       val movieDetails: StateFlow<NetworkResult<MovieResult>> get() = _movieDetails.asStateFlow()


    val products = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val productsListBySize = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val slider = MutableStateFlow<NetworkResult<Slider>>(NetworkResult.Loading())


    fun getProductBySize() {

        viewModelScope.launch {
            productsListBySize.emit(repository.getMoveListBySize())


        }
    }



    fun getProduct() {


        viewModelScope.launch {
            products.emit(repository.getProducts())


        }













        fun fetchMovieDetails(id: Int) {
            viewModelScope.launch {
                _movieDetails.value = NetworkResult.Loading()
                val result = repository.getMovieDetails(id)
                _movieDetails.value = result
            }


        }


            fun getProductBySize() {

                viewModelScope.launch {
                    productsListBySize.emit(repository.getMoveListBySize())


                }


            }
        }
    }




