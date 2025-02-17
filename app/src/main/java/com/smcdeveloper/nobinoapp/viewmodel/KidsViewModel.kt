package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smcdeveloper.nobinoapp.data.model.prducts.Delimiter
import com.smcdeveloper.nobinoapp.data.model.prducts.KidsBanner
import com.smcdeveloper.nobinoapp.data.model.prducts.KidsBannerPics
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.SpecialBanner
import com.smcdeveloper.nobinoapp.data.model.search.Countries
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import com.smcdeveloper.nobinoapp.data.repository.KidsRepository
import com.smcdeveloper.nobinoapp.data.source.ProductBySpecialCategoryDataSource
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG1
import com.smcdeveloper.nobinoapp.util.MovieDisplayData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KidsViewModel @Inject constructor(private val repository: KidsRepository):ViewModel() {


    private val _kidsBanner = MutableStateFlow<NetworkResult<List<KidsBanner?>>>(NetworkResult.Loading())
    val kidsBanner: StateFlow<NetworkResult<List<KidsBanner?>>> get() = _kidsBanner

    private val _slider2 = MutableStateFlow<NetworkResult<Slider>>(NetworkResult.Loading())
    val slider2: StateFlow<NetworkResult<Slider>> get() = _slider2.asStateFlow()


    private val _kidsBannerPics = MutableStateFlow<NetworkResult<KidsBannerPics>>(NetworkResult.Loading())
    val kidsBannerPics: StateFlow<NetworkResult<KidsBannerPics>> get() = _kidsBannerPics.asStateFlow()

















    private val _movieDisplayData2 =
        MutableStateFlow<NetworkResult<List<MovieDisplayData>?>>(NetworkResult.Loading())
    val movieDisplayData2: StateFlow<NetworkResult<List<MovieDisplayData>?>> get() = _movieDisplayData2.asStateFlow()

    private val _relatedProducts =
        MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val relatedProducts: StateFlow<NetworkResult<MovieResult>> get() = _relatedProducts










    fun fetchAllDataforKids(tagIds: List<Int>) {


        viewModelScope.launch {


            try {

                // Run both API calls in parallel
                val sliderFlow = async { repository.getSlider() } // Returns NetworkResult<Slider>
                val bannerFlow = async { repository.fetchKidsBanners(3,4) }


                val bannerPics = async { repository.getKidsBannerPics() }


                val moviesFlow = async {


                    val data =
                        repository.fetchMovieDisplayDataForkids(
                            tagIds
                        ) // Returns List<MovieDisplayData>
                    NetworkResult.Success(data) as NetworkResult<List<MovieDisplayData>?> // Explicitly match required type


                }

                // Wait for both responses
                val sliderResponse = sliderFlow.await()
                val moviesResponse = moviesFlow.await()
                val bannerResponse = bannerFlow.await()
                val bannerPicsResponse = bannerPics.await()

                _slider2.emit(sliderResponse)
                _movieDisplayData2.emit(moviesResponse)
                _kidsBanner.emit(bannerResponse) // Pass error state
                _kidsBannerPics.emit(bannerPicsResponse)

            }

            catch (e: Exception) {
                Log.e("API_ERROR", "Error fetching data: ${e.message}")
            }






            // Emit values after both responses are received

            //  _delimiter.emit(delimiterResponse)
        }


    }




















    }

