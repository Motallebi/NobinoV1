package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smcdeveloper.nobinoapp.data.model.prducts.Delimiter
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.SpecialBanner
import com.smcdeveloper.nobinoapp.data.model.search.Countries
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import com.smcdeveloper.nobinoapp.data.source.ProductBySpecialCategoryDataSource
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG1
import com.smcdeveloper.nobinoapp.util.MovieDisplayData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
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
class HomeViewModel @Inject constructor(private val repository: HomeRepository):ViewModel() {



    private var moviePager: Flow<PagingData<MovieResult.DataMovie.Item>>? =
        null // ✅ Cache the Flow, not the PagingData

    val slider1 = MutableStateFlow<NetworkResult<Slider>>(NetworkResult.Loading())
    val movieDisplayData1 =
        MutableStateFlow<NetworkResult<List<MovieDisplayData>?>>(NetworkResult.Loading())


    ////////////






    private val _apiError = MutableStateFlow(false)
    val apiError: StateFlow<Boolean> = _apiError




    private val _slider2 = MutableStateFlow<NetworkResult<Slider>>(NetworkResult.Loading())
    val slider2: StateFlow<NetworkResult<Slider>> get() = _slider2.asStateFlow()

    private val _delimiter = MutableStateFlow<NetworkResult<Delimiter>>(NetworkResult.Loading())
    val delimiter: StateFlow<NetworkResult<Delimiter>> get() = _delimiter.asStateFlow()


    private val _movieDisplayData2 =
        MutableStateFlow<NetworkResult<List<MovieDisplayData>?>>(NetworkResult.Loading())
    val movieDisplayData2: StateFlow<NetworkResult<List<MovieDisplayData>?>> get() = _movieDisplayData2.asStateFlow()


    private val _product = MutableStateFlow<NetworkResult<SpecialBanner>>(NetworkResult.Loading())
    val product: StateFlow<NetworkResult<SpecialBanner>> get() = _product

    private val _relatedProducts =
        MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val relatedProducts: StateFlow<NetworkResult<MovieResult>> get() = _relatedProducts


    /////////


    private val _movies = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val movies: StateFlow<NetworkResult<MovieResult>> get() = _movies.asStateFlow()


    private val _countries = MutableStateFlow<NetworkResult<Countries>>(NetworkResult.Loading())
    val contries: StateFlow<NetworkResult<Countries>> get() = _countries.asStateFlow()


    private val _moviesByTags = MutableStateFlow<NetworkResult<MovieCat>>(NetworkResult.Loading())
    val moviesByTags: StateFlow<NetworkResult<MovieCat>> get() = _moviesByTags.asStateFlow()

    private val _tags = MutableStateFlow<NetworkResult<MovieCat>>(NetworkResult.Loading())
    val Tags: StateFlow<NetworkResult<MovieCat>> get() = _tags.asStateFlow()


    private val _movieState =
        MutableStateFlow<NetworkResult<List<MovieResult.DataMovie.Item>>>(NetworkResult.Loading())
    val movieState: StateFlow<NetworkResult<List<MovieResult.DataMovie.Item>>> = _movieState

    private val _moviesByTag =
        MutableStateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>>(emptyMap())
    val moviesByTag: StateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>> =
        _moviesByTag

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

    private val _moviesByParameter =
        MutableStateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>>(emptyMap())
    val moviesByParameter: StateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>> =
        _moviesByParameter

    private val _tagsAndMovies =
        MutableStateFlow<Map<MovieCat.MovieCatData, NetworkResult<List<MovieResult.DataMovie.Item>>>>(
            emptyMap()
        )
    val tagsAndMovies: StateFlow<Map<MovieCat.MovieCatData, NetworkResult<List<MovieResult.DataMovie.Item>>>> =
        _tagsAndMovies

    //  private val _tagsAndMovies = MutableStateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>>(emptyMap())
    // val tagsAndMovies: StateFlow<Map<Int, NetworkResult<List<MovieResult.DataMovie.Item>>>> = _tagsAndMovies


    private val _moviesState = MutableStateFlow<List<NetworkResult<MovieResult>>>(emptyList())
    val moviesState: StateFlow<List<NetworkResult<MovieResult>>> = _moviesState




    private val _movieResults = MutableStateFlow<List<NetworkResult<MovieResult>>>(emptyList())
    val movieResults: StateFlow<List<NetworkResult<MovieResult>>> = _movieResults


    // private val _movieDisplayData = MutableStateFlow<List<MovieDisplayData>>(emptyList())
    // val movieDisplayData: StateFlow<List<MovieDisplayData>> = _movieDisplayData

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _movieDisplayData = MutableStateFlow<List<MovieDisplayData>?>(null)
    val movieDisplayData: StateFlow<List<MovieDisplayData>?> = _movieDisplayData


    private val _isLoading1 = MutableStateFlow(false) // 🔴 Loading state
    val isLoading1: StateFlow<Boolean> = _isLoading1.asStateFlow()


    var productByCategoryList: Flow<PagingData<MovieResult>> = flow { emit(PagingData.empty()) }

    // StateFlow to dynamically change the categoryId
    private val currentCategoryId = MutableStateFlow<String?>(null)

    private val processedTags = mutableSetOf<MovieCat.MovieCatData>()


    private val _moviesFlow1 =
        MutableStateFlow<PagingData<MovieResult.DataMovie.Item>>(PagingData.empty())
    val moviesFlow1: StateFlow<PagingData<MovieResult.DataMovie.Item>> = _moviesFlow1.asStateFlow()
    private var hasFetchedData = false













    // Expose the Flow of PagingData
    val moviesFlow: Flow<PagingData<MovieResult.DataMovie.Item>> = currentCategoryId
        .flatMapLatest { categoryId ->
            if (categoryId != null) {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        ProductBySpecialCategoryDataSource(
                            repository,
                            categoryId,
                            ""
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            } else {
                // Return an empty flow if no category is selected
                kotlinx.coroutines.flow.emptyFlow()
            }
        }


    fun fetchMovieDisplayData(tagIds: List<Int>) {
        viewModelScope.launch {
            _isLoading.value = true
            val data = repository.fetchMovieDisplayData(tagIds)
            _movieDisplayData.value = data
            _isLoading.value = false
        }
    }


    fun fetchCountries() {
        viewModelScope.launch {
            _isLoading.value = true
            val data = repository.getCountries()
            _countries.value = data
            _isLoading.value = false
        }
    }


    // Function to update the categoryId
    fun setCategoryId(categoryId: String) {
        currentCategoryId.value = categoryId
    }


    fun fetchMoviesForTags(tags: List<String>) {
        viewModelScope.launch {
            val results = repository.fetchAllMoviesResults(tags)
            _moviesState.value = results
        }
    }

    fun fetchMoviesForTagIds(tagIds: List<Int>) {
        viewModelScope.launch {
            _movieResults.value = repository.fetchMoviesForTags(tagIds)
        }
    }


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
                    val tag2 = tagResult.data?.movieCatData?.tags?.get(0).toString()

                    Log.d(NOBINO_LOG_TAG, "fetchMoviesForTagGroup: ${tag}")
                    Log.d(NOBINO_LOG_TAG, "fetchMoviesForTagGroup: ${tag2}")

                    // Fetch movies for this tag
                    repository.fetchMoviesByTag(tag2).map { moviesResult ->
                        tag2 to moviesResult
                    }
                        .onEach { (tag, result) ->

                            if (!tag.isNullOrEmpty()) {

                                val movieCatData: MovieCat.MovieCatData = MovieCat.MovieCatData(
                                    title = tagResult.data?.movieCatData?.title.toString(), // Assuming `tite` is the field you want to populate with the tag name
                                    tags = listOf(tag2), // Example: adding the tag to a list of tags
                                    count = 0, // Populate with appropriate values if available
                                    id = tagResult.data?.movieCatData?.id // Populate with appropriate values if available
                                )


                                /* val nonNullId = tagResult.data?.movieCatData?.id ?: -1 // Provide a default value for null IDs
                                if (!_tagsAndMovies.value.containsKey(nonNullId)) {

                                    Log.d(LOG_TAG, "State updated with tag: $tag")
                                }

                                else {
                                    Log.d(LOG_TAG, "Tag already exists: $tag")

                                }
*/









                                _tagsAndMovies.value =
                                    _tagsAndMovies.value + (movieCatData to result)

                            }


                        }


                } else {
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
            _isLoading.value = false


        }


    }


    fun getDelimiter() {


    }


    fun getAllDataFromServer(tagIds: List<Int>) {

        viewModelScope.launch {

            val sliderFlow = async { repository.getSlider() } // Returns NetworkResult<Slider>
            val moviesFlow =
                async { repository.fetchMovieDisplayData(tagIds).let { NetworkResult.Success(it) } }


            // Wait for both responses
            val sliderResponse = sliderFlow.await()
            val moviesResponse = moviesFlow.await()

            // Emit values after both responses are received
            slider1.emit(sliderResponse)
            // movieDisplayData1.emit(moviesResponse)


            launch {

                slider1.emit(repository.getSlider())


            }



            launch {

                val data = repository.fetchMovieDisplayData(tagIds = tagIds)
                //_movieDisplayData.value=data
                //  movieDisplayData1.emit(data)


            }


        }


    }


    fun fetchAllData(tagIds: List<Int>) {


        viewModelScope.launch {


            try {

                // Run both API calls in parallel
                val sliderFlow = async { repository.getSlider() } // Returns NetworkResult<Slider>
             //   val delimiterFlow = async { repository.getDelimiter() }
                val productFlow = async { repository.getSpecialBannerData() }


                val moviesFlow = async {


                    val data =
                        repository.fetchMovieDisplayData(tagIds) // Returns List<MovieDisplayData>
                    NetworkResult.Success(data) as NetworkResult<List<MovieDisplayData>?> // Explicitly match required type


                }

                // Wait for both responses
                val sliderResponse = sliderFlow.await()
                val moviesResponse = moviesFlow.await()
               // val delimiterResponse = delimiterFlow.await()
                val productResponse = productFlow.await()

                _slider2.emit(sliderResponse)
                _movieDisplayData2.emit(moviesResponse)


                // If product is fetched successfully, fetch related products using productId
                if (productResponse is NetworkResult.Success) {
                    _product.emit(productResponse)

                    val id = productResponse.data?.bannerData?.get(0)?.product?.id


                    val relatedProductsResponse = id?.let { repository.getRelatedEpisode(it) }

                    if (relatedProductsResponse != null) {
                        _relatedProducts.emit(relatedProductsResponse)
                    }
                } else {
                    _product.emit(productResponse) // Pass error state

                }
            }

            catch (e: Exception) {

                Log.e("API_ERROR", "Error fetching data: ${e.message}")

                _product.value= NetworkResult.Error("API_ERROR")




                _apiError.emit(true)


            }


            // Emit values after both responses are received

            //  _delimiter.emit(delimiterResponse)
        }


    }






    fun fetchAllMovieData(tagIds: List<Int>) {

        if (hasFetchedData) {
            Log.d("ProductViewModel", "Data already fetched. Skipping API call.")
            return
        }

        hasFetchedData = true

        fetchAllDataforKids(tagIds)




    }









    fun fetchAllDataforKids(tagIds: List<Int>) {


        viewModelScope.launch {


            try {

                // Run both API calls in parallel
                val sliderFlow = async { repository.getSlider() } // Returns NetworkResult<Slider>
                //   val delimiterFlow = async { repository.getDelimiter() }
                val productFlow = async { repository.getSpecialBannerData() }


                val moviesFlow = async {


                    val data =
                        repository.fetchMovieDisplayDataForkids(
                            tagIds) // Returns List<MovieDisplayData>
                    NetworkResult.Success(data) as NetworkResult<List<MovieDisplayData>?> // Explicitly match required type


                }

                // Wait for both responses
                val sliderResponse = sliderFlow.await()
                val moviesResponse = moviesFlow.await()
                // val delimiterResponse = delimiterFlow.await()
                val productResponse = productFlow.await()

                _slider2.emit(sliderResponse)

                _movieDisplayData2.emit(moviesResponse)


                // If product is fetched successfully, fetch related products using productId
                if (productResponse is NetworkResult.Success) {
                    _product.emit(productResponse)

                    val id = productResponse.data?.bannerData?.get(0)?.product?.id


                    val relatedProductsResponse = id?.let { repository.getRelatedEpisode(it) }

                    if (relatedProductsResponse != null) {
                        _relatedProducts.emit(relatedProductsResponse)
                    }
                } else {
                    _product.emit(productResponse) // Pass error state

                }
            }

            catch (e: Exception) {
                Log.e("API_ERROR", "Error fetching data: ${e.message}")

                _product.value= NetworkResult.Error("API_ERROR")


            }


            // Emit values after both responses are received

            //  _delimiter.emit(delimiterResponse)
        }


    }




    /////////
































    fun getMoviesByTags() {


        viewModelScope.launch {


            flow {
                // First API call
                val firstResponse = repository.getMoviesTag(1)
                _tags.value = firstResponse
                emit(firstResponse)
            }
                .map { firstData ->
                    // Extract parameters from the first API response
                    val paramA = firstData.data?.movieCatData?.tags?.get(0).toString()
                    val title = firstData.data?.movieCatData?.title


                    // val paramB = firstData.paramB

                    // Second API call using extracted parameters
                    repository.getMoveListBySize(paramA)
                }
                .catch { error ->
                    Log.d(NOBINO_LOG_TAG, "We have Error.......")
                    // Handle any errors
                    _moviesByTags.value = NetworkResult.Loading()
                }


        }


    }


    //todo it should be completed
    fun fetchMovies(tagId: Int) {


        repository.fetchMoviesByCategory(tagId)
            .onEach { result ->

                _movieState.value = result

                Log.d(NOBINO_LOG_TAG, ".......FETCH......")
                Log.d(NOBINO_LOG_TAG, ".......FETCH......" + _movieState.value.data.toString())


            }
            .launchIn(viewModelScope)
    }

    fun fetchMoviesByTag(tagId: Int) {
        _moviesByTag.value += (tagId to NetworkResult.Loading())

        repository.fetchMoviesByCategory(tagId)
            .onEach { result ->

                _moviesByTag.value += (tagId to result)

                Log.d(NOBINO_LOG_TAG, ".......FETCH......")
                Log.d(NOBINO_LOG_TAG, ".......FETCH......" + _moviesByTag.value.toString())


            }
            .launchIn(viewModelScope)
    }


    fun fetchMoviesForTags() {
        (1..3).forEach { tag ->
            Log.d(NOBINO_LOG_TAG, "00000000000000000")
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
    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchMoviesForTagGroup1(tagId: Int) {
        repository.fetchTagById(tagId)

            .flatMapConcat { tagResult ->
                if (tagResult is NetworkResult.Success) {
                    Log.d(NOBINO_LOG_TAG, "fetch by tagGroup Success-----")

                    val tag = MovieCat.MovieCatData(
                        title = tagResult.data?.movieCatData?.title,
                        tags = listOf(tagResult.data?.movieCatData?.tags.toString()),
                        count = 0,
                        id = tagId
                    )
                    repository.fetchMoviesByTag(tagResult.data?.movieCatData?.tags?.get(0) ?: "")
                        .map { moviesResult ->
                            tag to moviesResult
                        }
                } else {
                    Log.d(NOBINO_LOG_TAG, "fetch by tagGroup Error-----")
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


                //   _tagsAndMovies.value = _tagsAndMovies.value + (tag to result)
            }
            .launchIn(viewModelScope)
    }


    fun getMoviesByCategory1(
        tag: String,
        categoryName: String,
        countries: String,
        name: String,
        size: Int
    ) {
        Log.d(NOBINO_LOG_TAG, "getMoviesByCategory() called with tag: $tag")

        viewModelScope.launch {
            _isLoading1.value = true // ✅ Start loading before delay
            Log.d(NOBINO_LOG_TAG, "Loading started...")

            delay(2000) // ⏳ Simulate loading delay

            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    Log.d(NOBINO_LOG_TAG, "Creating new MoviePagingSource with tag: $tag")
                    ProductBySpecialCategoryDataSource(repository, tag, categoryName, countries, name, size)
                }
            ).flow
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _moviesFlow1.value = pagingData // ✅ Emit data to UI
                    _isLoading1.value = false // ✅ Stop loading when data is received
                    Log.d(NOBINO_LOG_TAG, "Data loaded successfully.")
                }
        }
    }




































    fun getMoviesByCategory(tag: String,categoryName:String,countries:String,name:String,size:Int): Flow<PagingData<MovieResult.DataMovie.Item>> {
        Log.d(NOBINO_LOG_TAG,"getmovie......")
        Log.d(NOBINO_LOG_TAG, "tag is......${tag.toString()}")

        return Pager(
            config = PagingConfig(
                pageSize =20, // Page size
                initialLoadSize = 20,
                enablePlaceholders = false
            ),


            pagingSourceFactory = {

                Log.d(NOBINO_LOG_TAG, "Creating new MoviePagingSource with categoryId: ${tag.toString()}")

                ProductBySpecialCategoryDataSource(repository, tagName =tag,categoryName= categoryName, countries =countries, name = name, size = size )


            }
        ).flow.cachedIn(viewModelScope)
    }







    private val _backgroundImageUrl = MutableStateFlow<String?>(null) // Holds image URL
    val backgroundImageUrl: StateFlow<String?> = _backgroundImageUrl.asStateFlow()

    fun updateBackground(url: String?) {
        _backgroundImageUrl.value = url



    }


    fun resetBackground() {
        _backgroundImageUrl.value = null // Reset background when returning to home
    }















    fun getMoviesByCategory3(
        tag: String,
        categoryName: String,
        countries: String,
        name: String,
        size: Int
    ): Flow<PagingData<MovieResult.DataMovie.Item>> {
        if (moviePager != null) {
            return moviePager!! // ✅ Prevents re-triggering the Pager unnecessarily
        }

        Log.d(NOBINO_LOG_TAG, "🎬 Fetching movies for tag=$tag")

         moviePager = Pager(
            config = PagingConfig(
                pageSize = size,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                Log.d(NOBINO_LOG_TAG, "📡 Creating new PagingSource for tag=$tag")
                ProductBySpecialCategoryDataSource(repository, tag, categoryName, countries, name, size)
            }
        ).flow
            .cachedIn(viewModelScope)

       return moviePager!!  // ✅ Cache the data


    }






























    fun fetchMoviesByTags(tagIds: List<Int>) {
        tagIds
            .asFlow() // Convert list of tag IDs to a flow
            .flatMapConcat { tagId ->
                repository.fetchTagById(tagId)
                .flatMapConcat { tagResult ->
                    if (tagResult is NetworkResult.Success) {
                        Log.d(NOBINO_LOG_TAG, "fetch by tagGroup Success-----")

                        val tag = MovieCat.MovieCatData(
                            title = tagResult.data?.movieCatData?.title,
                            tags = listOf(tagResult.data?.movieCatData?.tags.toString()),
                            count = 0,
                            id = tagId
                        )

                        repository.fetchMoviesByTag(tagResult.data?.movieCatData?.tags?.get(0) ?: "")
                            .map { moviesResult ->
                                tag to moviesResult
                            }
                    } else {
                        Log.d(NOBINO_LOG_TAG, "fetch by tagGroup Error-----")
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
            }
            .onEach { (tag, result) ->


                /*val nonNullId = tag.id ?: -1 // Provide a default value for null IDs
                if (!_tagsAndMovies.value.containsKey(nonNullId)) {
                    _tagsAndMovies.value = _tagsAndMovies.value + (nonNullId to result)
                    Log.d(LOG_TAG, "State updated with tag: $tag")
                }

                else {
                    Log.d(LOG_TAG, "Tag already exists: $tag")

                }*/







                _tagsAndMovies.value = _tagsAndMovies.value + (tag to result)
            }
            .catch { e ->
                Log.e(NOBINO_LOG_TAG, "Error fetching movies: ${e.message}")
            }
            .launchIn(viewModelScope)
    }

    fun processTagsAndMovies(tagsAndMoviesState: Map<MovieCat.MovieCatData, NetworkResult<List<MovieResult.DataMovie.Item>>>) {
        Log.d(NOBINO_LOG_TAG,"----processTagsAndMovies----")
        tagsAndMoviesState.forEach { (tag, result) ->
            // Only process if the tag hasn't been processed yet
            if (tag !in processedTags) {
                processedTags.add(tag) // Mark the tag as processed

                // Process the tag and its result
                when (result) {
                    is NetworkResult.Success -> {
                        // Handle success case
                        Log.d(NOBINO_LOG_TAG, "Processing tag: ${tag.title}, movies: ${result.data}")
                    }
                    is NetworkResult.Error -> {
                        // Handle error case
                        Log.e(NOBINO_LOG_TAG, "Error processing tag: ${tag.title}, error: ${result.message}")
                    }
                    is NetworkResult.Loading -> {
                        Log.d(NOBINO_LOG_TAG,"----processTagsAndMovies----Loading")
                        // Handle loading state if necessary
                        Log.d(NOBINO_LOG_TAG, "Loading movies for tag: ${tag.title}")
                    }
                }
            }
        }
    }



    private val processedTagIds = mutableSetOf<Int>()

    fun fetchMoviesByTags1(tagIds: List<Int>) {
        tagIds
            .asFlow()
            .filter { tagId -> // Skip already processed tags

                val shouldProcess = !processedTagIds.contains(tagId)
                Log.d(NOBINO_LOG_TAG, "Tag ID: $tagId, Should Process: $shouldProcess")
                if (shouldProcess) processedTagIds.add(tagId)
                shouldProcess







                if (processedTagIds.contains(tagId)) {
                    Log.d(NOBINO_LOG_TAG, "Skipping already processed tagId: $tagId")
                    false
                } else {
                    processedTagIds.add(tagId)
                    true
                }
            }



            .flatMapConcat { tagId ->
                repository.fetchTagById(tagId)
                    .flatMapConcat { tagResult ->
                        if (tagResult is NetworkResult.Success) {
                            val tag = MovieCat.MovieCatData(
                                title = tagResult.data?.movieCatData?.title ?: "Unknown",
                                tags = tagResult.data?.movieCatData?.tags?.mapNotNull { it } ?: emptyList(),
                                count = tagResult.data?.movieCatData?.count ?: 0,
                                id = tagId
                            )
                            val firstTag = tagResult.data?.movieCatData?.tags?.getOrNull(0)
                            Log.d(NOBINO_LOG_TAG,"tags new..${firstTag}")
                            if (!firstTag.isNullOrEmpty()) {
                                repository.fetchMoviesByTag(firstTag)
                                    .map { moviesResult ->
                                        tag to moviesResult
                                    }
                            } else {

                                flowOf(tag to NetworkResult.Error<List<MovieResult.DataMovie.Item>>(
                                    Exception("No valid tag").toString()
                                )
                                )
                            }
                        } else {
                            flowOf(
                                MovieCat.MovieCatData(
                                    title = "Unknown",
                                    tags = emptyList(),
                                    count = 0,
                                    id = tagId
                                )
                                        to tagResult as NetworkResult<List<MovieResult.DataMovie.Item>>

                            )

                        }
                    }
            }
            .onEach { (tag, result) ->
                Log.d(NOBINO_LOG_TAG,"888888888"+tag.title.toString())

                /*val nonNullId = tag.id ?: -1 // Provide a default value for null IDs
                if (!_tagsAndMovies.value.containsKey(nonNullId)) {
                    _tagsAndMovies.value = _tagsAndMovies.value + (nonNullId to result)
                    Log.d(LOG_TAG, "State updated with tag: $tag")
                }

                else {
                    Log.d(LOG_TAG, "Tag already exists: $tag")

                }*/






                _tagsAndMovies.value = _tagsAndMovies.value + (tag to result)


              /*  if (!_tagsAndMovies.value.containsKey(tag)) {
                  //  Log.d(LOG_TAG,"999999999"+tag.title.toString())
                    Log.d(LOG_TAG, "Updating state for tag: ${tag.title}, result: $result")
                    _tagsAndMovies.value = _tagsAndMovies.value + (tag to result)*/




                }

            .catch { e ->
                Log.e(NOBINO_LOG_TAG, "Error fetching movies: ${e.message}")
            }
            .launchIn(viewModelScope)
    }

    fun fetchMoviesByTags2(tagIds: List<Int>) {
        tagIds
            .asFlow()
            .filter { tagId ->
                val shouldProcess = !processedTagIds.contains(tagId)
                if (shouldProcess) processedTagIds.add(tagId)
                Log.d(NOBINO_LOG_TAG, "Tag ID: $tagId, Should Process: $shouldProcess")
                shouldProcess
            }
            .flatMapConcat { tagId ->
                repository.fetchTagById(tagId)
                    .flatMapConcat { tagResult ->
                        if (tagResult is NetworkResult.Success) {
                            val tag = MovieCat.MovieCatData(
                                title = tagResult.data?.movieCatData?.title ?: "Unknown",
                                tags = tagResult.data?.movieCatData?.tags?.mapNotNull { it } ?: emptyList(),
                                count = tagResult.data?.movieCatData?.count ?: 0,
                                id = tagId
                            )
                            val firstTag = tagResult.data?.movieCatData?.tags?.getOrNull(0)
                            if (!firstTag.isNullOrEmpty()) {
                                repository.fetchMoviesByTag(firstTag)
                                    .map { moviesResult ->
                                        tag to moviesResult
                                    }
                            } else {
                                flowOf(tag to NetworkResult.Error<List<MovieResult.DataMovie.Item>>(
                                    Exception("No valid tag").toString()
                                ))
                            }
                        } else {
                            flowOf(
                                MovieCat.MovieCatData(
                                    title = "Unknown",
                                    tags = emptyList(),
                                    count = 0,
                                    id = tagId
                                ) to tagResult as NetworkResult<List<MovieResult.DataMovie.Item>>
                            )
                        }
                    }
            }
            .onEach { (tag, result) ->
                Log.d(NOBINO_LOG_TAG, "Updating state for tag: ${tag.title}, Result: $result")
                //_tagsAndMovies.value = _tagsAndMovies.value + (tag to result)
                /* val nonNullId = tag.id ?: -1 // Provide a default value for null IDs
                if (!_tagsAndMovies.value.containsKey(nonNullId)) {
                    _tagsAndMovies.value = _tagsAndMovies.value + (nonNullId to result)
                }

            }*/
                _tagsAndMovies.value = _tagsAndMovies.value + (tag to result)

            } .catch { e ->
                Log.e(NOBINO_LOG_TAG, "Error fetching movies: ${e.message}")
            }
            .launchIn(viewModelScope)
    }


    fun getMoviesByCategory2(
        tag: String,
        categoryName: String,
        countries: String,
        name: String,
        size: Int
    ): Flow<PagingData<MovieResult.DataMovie.Item>> {
        Log.d(NOBINO_LOG_TAG, "getMoviesByCategory() called with tag: $tag")

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                Log.d(NOBINO_LOG_TAG, "Creating new MoviePagingSource with tag: $tag")
                ProductBySpecialCategoryDataSource(repository, tag, categoryName, countries, name, size)
            }
        ).flow
            .onStart {
                _isLoading1.value = true  // ✅ Show loading state immediately
                Log.d(NOBINO_LOG_TAG, "Loading started...")

                viewModelScope.launch {
                    delay(2000) // ✅ Simulate a small delay without blocking the flow
                    _isLoading1.value = false // ✅ Hide loading after delay
                    Log.d(NOBINO_LOG_TAG, "Simulated delay finished. Loading state updated.")
                }
            }
            .catch { e ->
                _isLoading1.value = false // ❌ Hide loading on error
                Log.e(NOBINO_LOG_TAG, "Error fetching movies: ${e.message}", e)
            }
            .cachedIn(viewModelScope)
    }
























}























 /*fun getProductsBySpecialTag(tag:String) {
    Log.e(LOG_TAG,"Route..........")

     productByCategoryList = Pager(

         PagingConfig(pageSize = 10),


     )

     {
         ProductBySpecialCategoryDataSource(repository, tag, 1)


     }.flow.cachedIn(viewModelScope)


 }*/













