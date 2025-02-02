package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.datastore.DataStoreRepository
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductCategories
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProductDetailsRepository
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel.Companion.USER_TOKEN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductDetailsRepository,
    private val dataStoreRepository:DataStoreRepository
) : ViewModel() {

    private val _product = MutableStateFlow<NetworkResult<ProductModel>>(NetworkResult.Loading())
    val product: StateFlow<NetworkResult<ProductModel>> get() = _product.asStateFlow()

    private val _relatedMovies =
        MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val relatedMovies: StateFlow<NetworkResult<MovieResult>> get() = _relatedMovies.asStateFlow()

    private val _relatedSeries = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val relatedSeries: StateFlow<NetworkResult<MovieResult>> get() = _relatedSeries.asStateFlow()









    // Fetch product details
    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            Log.d("productdatils", "product id is:...${productId}")
            val token = dataStoreRepository.getString(USER_TOKEN_KEY).toString()



            //val results = repository.getProductDetails(productId,"Bearer "+token)
            Log.d("productdatils", "product id is:...${productId}")
            Log.d("Token", "product id is:...${token}")



            _product.value = NetworkResult.Loading()
            //_product.value = repository.getProductDetails(productId, auth = "Bearer " + token)
            _product.value = repository.getProductDetails(productId, auth = "")
        }
    }


    // Fetch related movies
    fun getRelatedMovies(tags: String ) {
        Log.d(
            "ProductDetailsViewModel",
            "Fetching related movies. Product ID: , Tags: $tags"
        )
        viewModelScope.launch {
            _relatedMovies.value = NetworkResult.Loading()
            Log.d("ProductDetailsViewModel", "Related movies loading state set.")

            val result = repository.fetchRelatedMovies(tags)
            _relatedMovies.value = result

            when (result) {
                is NetworkResult.Success -> {
                    Log.d(
                        "ProductDetailsViewModel",
                        "Related movies fetched successfully: ${result.data}"
                    )
                }

                is NetworkResult.Error -> {
                    Log.e(
                        "ProductDetailsViewModel",
                        "Error fetching related movies: ${result.message}"
                    )
                }

                is NetworkResult.Loading -> {
                    Log.d("ProductDetailsViewModel", "Related movies are still loading.")
                }
            }
        }
    }





    fun getSeriesEpisodes(seriesId:Int) {

        viewModelScope.launch {
            Log.d("series","product id is$seriesId")
            //  seriesListBySize.emit(repository.getSeriesProductBysize())
            _relatedSeries.value = NetworkResult.Loading()

            val result = repository.getSeriesEpisodes(seriesId)
           // result.data.movieInfo.items.get(0).id

            Log.d("series","total episodes"+result.data?.movieInfo?.total.toString())
           // Log.d("series","total episodes"+result.data.movieInfo.i)

            result.data?.movieInfo?.items?.forEach { movie->
                Log.d("series","series..."+movie?.name.toString())



            }



            _relatedSeries.value=result

        }
    }






    fun getSeriesEpisodes2(seriesId: Int,seriesNum:Int) {
        viewModelScope.launch {
            try {
                Log.d("series", "Product ID is $seriesId")
                _relatedSeries.value = NetworkResult.Loading()

                // First API call to get series episodes
                val result = repository.getSeriesEpisodes(seriesId)
                Log.d("series", "Total episodes: ${result.data?.movieInfo?.total}")

                // Extract the ID from the first result
                val firstItemId = result.data?.movieInfo?.items?.get(seriesNum)?.id
                if (firstItemId == null) {
                    Log.e("series", "No items found in series")
                    _relatedSeries.value = NetworkResult.Error("No items found in series")
                    return@launch
                }

                Log.d("series", "First item ID: $firstItemId")

                // Second API call using the extracted ID
                val movieResult = repository.getSeriesEpisodes(firstItemId)
                Log.d("series", "Movie result retrieved successfully")

                // Process the result as needed
                movieResult.data?.movieInfo?.items?.forEach { movie ->
                    Log.d("series", "Movie: ${movie?.name}")
                }

                // Set the result of the second API call to the state
                _relatedSeries.value = movieResult
            } catch (e: Exception) {
                Log.e("series", "Error: ${e.message}")
                _relatedSeries.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getSeriesEpisodes3(seriesId: Int) {
        viewModelScope.launch {
            try {
                Log.d("series", "Product ID is $seriesId")
                _relatedSeries.value = NetworkResult.Loading()

                // First API call to fetch the initial series details
                val result = repository.getSeriesEpisodes(seriesId)
                val items = result.data?.movieInfo?.items

                if (items.isNullOrEmpty()) {
                    Log.e("series", "No items found in series")
                    _relatedSeries.value = NetworkResult.Error("No items found in series")
                    return@launch
                }

                Log.d("series", "Total episodes: ${result.data.movieInfo.total}")

                // Process each item and fetch MovieResult details
                val allMovieResults = items.mapNotNull { item ->
                    val itemId = item?.id
                    if (itemId != null) {
                        try {
                            Log.d("series", "Fetching details for item ID: $itemId")
                            repository.getSeriesEpisodes(itemId) // API call returning single MovieResult
                        } catch (e: Exception) {
                            Log.e("series", "Error fetching details for item ID: $itemId, ${e.message}")
                            null // Skip failed API calls
                        }
                    } else {
                        null // Skip items with null IDs
                    }
                }

                // Update state with the list of MovieResult objects
               // _relatedSeries.value = NetworkResult.Success(allMovieResults)

            } catch (e: Exception) {
                Log.e("series", "Error: ${e.message}")
                _relatedSeries.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

















}








/* // Fetch related movies
 fun getRelatedMovies(productId: Int, tags: String = "") {
     viewModelScope.launch {
         _relatedMovies.value = NetworkResult.Loading()
         _relatedMovies.value = repository.fetchRelatedMovies(productId, tags)
     }
 }
}*/
