package com.smcdeveloper.nobinoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import com.smcdeveloper.nobinoapp.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val repository: SeriesRepository):ViewModel() {

    private val _series = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val series: StateFlow<NetworkResult<MovieResult>> get() = _series.asStateFlow()

    private val _seriesDetails =
        MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val seriesDetails: StateFlow<NetworkResult<MovieResult>> get() = _seriesDetails.asStateFlow()


    val products = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val seriesListBySize = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())


    val _seriesEpisodes = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val seriesEpisodes: StateFlow<NetworkResult<MovieResult>> get() = _seriesEpisodes.asStateFlow()

    private val _series1 = MutableStateFlow<NetworkResult<ProductModel>>(NetworkResult.Loading())
    //val product: StateFlow<NetworkResult<ProductModel>> get() = _product.asStateFlow()








    fun getSeriestBySize() {

        viewModelScope.launch {
          //  seriesListBySize.emit(repository.getSeriesProductBysize())
            _series.value = NetworkResult.Loading()
            val result = repository.getSeriesProductBysize()
            _series.value=result

        }
    }











    fun fetchSeriesDetails(id: Int) {
        viewModelScope.launch {
            _seriesDetails.value = NetworkResult.Loading()
            val result = repository.getSeriesDetails(id)
            _seriesDetails.value = result
        }


    }




    }







