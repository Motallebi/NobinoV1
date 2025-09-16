package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.datastore.DataStoreRepository
import com.smcdeveloper.nobinoapp.data.model.advertise.Advertise
import com.smcdeveloper.nobinoapp.data.model.prducts.BookMarKRequest
import com.smcdeveloper.nobinoapp.data.model.prducts.BookMark
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProductDetailsRepository
import com.smcdeveloper.nobinoapp.util.Constants.USER_LOGIN_STATUS
import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel.Companion.USER_TOKEN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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

    private val _episodes = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val episodes: StateFlow<NetworkResult<MovieResult>> get() = _episodes.asStateFlow()

    private val _lastSessionepisodes = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val lastSessionepisodes: StateFlow<NetworkResult<MovieResult>> get() = _lastSessionepisodes.asStateFlow()


    private val _productAdv=MutableStateFlow<NetworkResult<Advertise>>(NetworkResult.Loading())
    val productAdv: StateFlow<NetworkResult<Advertise>>  = _productAdv.asStateFlow()
    val _episodeIds =MutableStateFlow<List<Int>>(emptyList())
    val episodeIds: StateFlow<List<Int>> = _episodeIds.asStateFlow()











    private val _episodes1 = MutableStateFlow<NetworkResult<List<MovieResult.DataMovie.Item>>>(NetworkResult.Loading())
    val episodes1: StateFlow<NetworkResult<List<MovieResult.DataMovie.Item>>> get() = _episodes1.asStateFlow()


    val _saveBookMarkResponse = MutableStateFlow<NetworkResult<BookMark>>(NetworkResult.Loading())
    val saveBookMarkResponse: StateFlow<NetworkResult<BookMark>> get() = _saveBookMarkResponse.asStateFlow()

    val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()




    private val _firstEpisode = MutableStateFlow<NetworkResult<ProductModel>>(NetworkResult.Loading())
    val firstEpisode: StateFlow<NetworkResult<ProductModel>> get() = _firstEpisode.asStateFlow()

    private val _isUserLoging = MutableStateFlow(false)
    val isUserLoging : StateFlow<Boolean> = _isUserLoging.asStateFlow()


    fun getProductAdv(productId: Int) {
        viewModelScope.launch {


            _productAdv.value = NetworkResult.Loading()

            _productAdv.value = repository.getProductAdv(productId)


        }

    }

fun saveEpisodeIds(episodeId:Int) {
    val currentList = _episodeIds.value.toMutableList()
    currentList.add(episodeId)
    _episodeIds.value = currentList

}

fun getEpisodeIds():List<Int>
{
    return _episodeIds.value


}












    fun ChangeUserLogin()
    {
        _isUserLoging.value=true





    }




    // Fetch product details
    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            Log.d("productdatils", "product id is:...${productId}")
            val token = dataStoreRepository.getString(USER_TOKEN_KEY).toString()



            //val results = repository.getProductDetails(productId,"Bearer "+token)
            Log.d("productdatils", "product id is:...${productId}")
            Log.d("Token", "product id is:...${token}")
            _product.value = NetworkResult.Loading()

            if(USER_TOKEN_KEY.isNotBlank() && USER_LOGIN_STATUS) {

                _product.value = repository.getProductDetails(productId, auth = "Bearer " + token)
                _isBookmarked.value=_product.value.data?.data?.bookmarked!!
            }

            else

            _product.value = repository.getProductDetails(productId)







            //_product.value = repository.getProductDetails(productId, auth = "")
        }
    }



    fun saveBookMark(bookmark:BookMarKRequest,auth:String)
    {
        viewModelScope.launch {


          val result=  repository.saveBookMark(bookmark,auth)

            _saveBookMarkResponse.value=result




            _isBookmarked.value =  result.data?.data?.bookmarked!!





        }






    }





    fun removeBookMark(bookmark:BookMarKRequest,auth:String)
    {
        viewModelScope.launch {

            val result1=  repository.removeBookMark(
                bookMarKRequest = BookMarKRequest(12965,type="BOOKMARK"),
                auth = auth




            )

            val result=  repository.removeBookMark(bookmark,auth)

            Log.d("book","result is ${result.data.toString()}")
            Log.d("book",auth)


            _saveBookMarkResponse.value=result
            _isBookmarked.value =  result.data?.data?.bookmarked!!






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









    fun getRelatedMovies(tags: List<String> ) {
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
                    result.data?.movieInfo?.items?.forEach {
                        Log.d(
                            "ProductDetailsViewModel",
                            "Related movies fetched successfully: ${it?.id}"


                        )

                    }





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
            _episodes.value = NetworkResult.Loading()

            val result = repository.getSeriesEpisodes(seriesId)
           // result.data.movieInfo.items.get(0).id

            Log.d("series","total episodes"+result.data?.movieInfo?.total.toString())
           // Log.d("series","total episodes"+result.data.movieInfo.i)

            result.data?.movieInfo?.items?.forEach { movie->
                Log.d("series","series..."+movie?.name.toString())



            }



            _episodes.value=result

        }
    }






    fun getSeriesEpisodes2(seriesId: Int,seriesNum:Int) {
        viewModelScope.launch {
            try {
                Log.d("series", "Product ID is $seriesId")
                _episodes.value = NetworkResult.Loading()

                // First API call to get series episodes
                val result = repository.getSeriesEpisodes(seriesId)

                Log.d("series", "Total episodes: ${result.data?.movieInfo?.total}")

                // Extract the ID from the first result

                val firstItemId = result.data?.movieInfo?.items?.get(seriesNum)?.id
                if (firstItemId == null) {
                    Log.e("series", "No items found in series")
                    _episodes.value = NetworkResult.Error("No items found in series")
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
                _episodes.value = movieResult
            } catch (e: Exception) {
                Log.e("series", "Error: ${e.message}")
                _episodes.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getSeriesEpisodes3(seriesId: Int) {
        viewModelScope.launch {
            try {
                Log.d("series", "Product ID is $seriesId")
                _episodes.value = NetworkResult.Loading()

                // First API call to fetch the initial series details
                val result = repository.getSeriesEpisodes(seriesId)
                val items = result.data?.movieInfo?.items

                if (items.isNullOrEmpty()) {
                    Log.e("series", "No items found in series")
                    _episodes.value = NetworkResult.Error("No items found in series")
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
                _episodes.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }












fun getSeriesLastSessionEpisodes(productId: Int)
{
    viewModelScope.launch {

        _lastSessionepisodes.value=NetworkResult.Loading()

         val result= repository.getSeriesEpisodes(productId)
         val totalItemSize= result.data?.movieInfo?.items?.size ?: 1

       val item=  result.data?.movieInfo?.items?.get(totalItemSize-1)
        val relatedId= item?.id

        if (relatedId!=null)
        {

            val data= repository.getSeriesEpisodes(relatedId)
            if(data.code==403)
            {
                _lastSessionepisodes.value=NetworkResult.Error(data.message.toString())
                return@launch

            }
            data.data?.movieInfo?.items?.forEach  {
                Log.d("SessionData", "Session ${it?.name.toString()}")
            }

            _lastSessionepisodes.value=NetworkResult.Success(data.data)



}










    }







}










    fun getSeriesEpisodes4(seriesId: Int, seriesNum: Int) {
        viewModelScope.launch {
            try {
                Log.d("series", "Product ID is $seriesId")
                _episodes1.value = NetworkResult.Loading()

                // First API call to get series episodes
                val result = repository.getSeriesEpisodes(seriesId)
                val totalItemSize= result.data?.movieInfo?.items?.size ?: 1







                Log.d("series", "Total episodes: ${result.data?.movieInfo?.total}")

                Log.d("series", "series is: ${seriesId} series num ${seriesNum}")


                // Extract the session ID
                val firstItemId = result.data?.movieInfo?.items?.getOrNull(seriesNum)?.id
                if (firstItemId == null) {
                    Log.e("series", "No items found in series")
                    _episodes1.value = NetworkResult.Error("No items found in series")
                    return@launch
                }

                Log.d("series", "First item ID: $firstItemId")

                // Second API call to get episodes
                val movieResult = repository.getSeriesEpisodes(firstItemId)
                val currentEpisodes=
                Log.d("series", "Movie result retrieved successfully")

                val episodeList = movieResult.data?.movieInfo?.items?.filterNotNull().orEmpty()




                // ✅ Store only the list of episodes, not the whole response
                _episodes1.value = NetworkResult.Success(episodeList)



            } catch (e: Exception) {
                Log.e("series", "Error: ${e.message}")
                _episodes1.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }





    fun getSeriesEpisodes5(seriesId: Int, sessionNum: Int) {
        viewModelScope.launch {
            try {
                Log.d("series", "Product ID is $seriesId")
                _episodes1.value = NetworkResult.Loading()

                // First API call to get series episodes
                val result = repository.getSeriesEpisodes(seriesId)
                val totalItemSize= result.data?.movieInfo?.items?.size ?: 1


                val item=  result.data?.movieInfo?.items?.get(sessionNum)
                val relatedId= item?.id

                val data= repository.getSeriesEpisodes(relatedId!!)










                Log.d("series", "Total episodes: ${result.data?.movieInfo?.total}")

              //  Log.d("series", "series is: ${seriesId} series num ${seriesNum}")


                // Extract the session ID
               // val firstItemId = result.data?.movieInfo?.items?.getOrNull(seriesNum)?.id
                if (relatedId == null) {
                    Log.e("series", "No items found in series")
                    _episodes1.value = NetworkResult.Error("No items found in series")
                    return@launch
                }

                Log.d("series", "First item ID: $relatedId")

                // Second API call to get episodes
                val movieResult = repository.getSeriesEpisodes(relatedId)
                val currentEpisodes=
                    Log.d("series", "Movie result retrieved successfully")

             //   val episodeList = movieResult.data?.movieInfo?.items?.filterNotNull().orEmpty()




                // ✅ Store only the list of episodes, not the whole response
             //   _episodes1.value = NetworkResult.Success(episodeList)
                _lastSessionepisodes.value=NetworkResult.Success(movieResult.data)


            } catch (e: Exception) {
                Log.e("series", "Error: ${e.message}")
                _episodes1.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
















    fun getSeriesFirtsEpisodes1(seriesId: Int, seriesNum: Int) {
        viewModelScope.launch {
            try {
                Log.d("series", "Product ID is $seriesId")
                _firstEpisode.value = NetworkResult.Loading()

                // First API call to get series episodes
                val result = repository.getSeriesEpisodes(seriesId)
                Log.d("series", "Total episodes: ${result.data?.movieInfo?.total}")

                Log.d("series", "series is: ${seriesId} series num ${seriesNum}")


                // Extract the session ID
                val firstItemId = result.data?.movieInfo?.items?.getOrNull(seriesNum)?.id
                if (firstItemId == null) {
                    Log.e("series", "No items found in series")
                    _firstEpisode.value = NetworkResult.Error("No items found in series")
                    return@launch
                }

                Log.d("series", "First item ID: $firstItemId")

                // Second API call to get episodes
                val movieResult = repository.getSeriesEpisodes(firstItemId)
                Log.d("series", "Movie result retrieved successfully")

                val firstEpisodeData = movieResult.data?.movieInfo?.items?.filterNotNull().orEmpty()



                // ✅ Store only the list of episodes, not the whole response
                val episodeId = firstEpisodeData[0].id
                Log.d("series",_firstEpisode.value.data.toString())
                Log.d("series", USER_TOKEN_KEY)


               val episodeResult= repository.getProductDetails(episodeId!!, "Bearer $USER_TOKEN")
                _firstEpisode.value=episodeResult




            } catch (e: Exception) {
                Log.e("series", "Error: ${e.message}")
                _firstEpisode.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }



    fun getSeriesFirstEpisode(seriesId: Int, seriesNum: Int) {
        viewModelScope.launch {
            try {
                Log.d("series", "Product ID is $seriesId")

                // ✅ Show loading state
                _firstEpisode.value = NetworkResult.Loading()

                // 🔹 First API call (async)
                val seriesResultDeferred = async { repository.getSeriesEpisodes(seriesId) }
                val result = seriesResultDeferred.await()
                Log.d("series", "Total episodes: ${result.data?.movieInfo?.total}")

                // Extract session ID
                val firstItemId = result.data?.movieInfo?.items?.getOrNull(seriesNum)?.id
                if (firstItemId == null) {
                    Log.e("series", "No items found in series")
                    _firstEpisode.value = NetworkResult.Error("No items found in series")
                    return@launch
                }

                // 🔹 Second API call (async)
                val movieResultDeferred = async { repository.getSeriesEpisodes(firstItemId) }
                val movieResult = movieResultDeferred.await()
                Log.d("series", "Movie result retrieved successfully")

                val firstEpisodeData = movieResult.data?.movieInfo?.items?.filterNotNull().orEmpty()
                if (firstEpisodeData.isEmpty()) {
                    Log.e("series", "No episodes found")
                    _firstEpisode.value = NetworkResult.Error("No episodes found")
                    return@launch
                }

                val episodeId = firstEpisodeData[0].id
                Log.d("series", "Episode ID: $episodeId")

                // 🔹 Third API call (async)
                val episodeResultDeferred = async {
                    repository.getProductDetails(episodeId!!, "Bearer $USER_TOKEN")
                }
                val episodeResult = episodeResultDeferred.await()

                // ✅ Store final result (Success state)
                _firstEpisode.value = episodeResult

            } catch (e: Exception) {
                Log.e("series", "Error: ${e.message}")
                _firstEpisode.value = NetworkResult.Error(e.message ?: "Unknown error")
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
