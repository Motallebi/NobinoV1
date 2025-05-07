package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.search.Countries
import com.smcdeveloper.nobinoapp.data.model.search.SearchParams
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.SearchRepository
import com.smcdeveloper.nobinoapp.data.source.ProductBySpecialCategoryDataSource
import com.smcdeveloper.nobinoapp.data.source.SearchDataSource
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository):ViewModel() {

   /// test search param..

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedProducts = MutableStateFlow<PagingData<MovieResult.DataMovie.Item>>(PagingData.empty())
    val searchedProducts = _searchedProducts

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {







            repository.searchProduct(query = query).cachedIn(viewModelScope).collect {
                _searchedProducts.value = it
            }
        }
    }




///////// end of test search
















    private val _countries = MutableStateFlow<NetworkResult<Countries>>(NetworkResult.Loading())
    val contries: StateFlow<NetworkResult<Countries>> get() = _countries.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()


    private val _searchParams = MutableStateFlow(SearchParams())
    val searchParams: StateFlow<SearchParams> = _searchParams


    fun updateSearchParams(query:String , tag: String, category: List<String>, countries: String,persons:String) {
        _searchParams.value = SearchParams(query, tag, listOf("MOVIE","SERIES"), countries,persons)

    }





    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val moviesFlow = _searchParams
        .debounce(500L)
        .onEach { params ->
            Log.d("SearchViewModel", "Received search parameters: $params")
        }
        .flatMapLatest { params ->
            if (params.query.isBlank() && params.tag.isBlank() && false) {
                Log.d("SearchViewModel", "Query is blank. Emitting empty PagingData.")
                flowOf(PagingData.empty())
            }


            else if(params.query.isBlank() && params.tag.isBlank())
            {
                Pager(
                    config = PagingConfig(pageSize = 10, enablePlaceholders = false,
                        initialLoadSize = 18

                    ),
                    pagingSourceFactory = {
                        SearchDataSource(
                            repository = repository,
                            tagName = params.tag,
                            categoryName = listOf("SERIES,MOVIE,COURSE,CERTIFICATED_COURSE"),
                            countries = params.countries,
                            name = " ",
                            persons = params.persons,
                            size = 18

                        )
                    }
                ).flow.cachedIn(viewModelScope)




            }








            else {



                Log.d(
                    "SearchViewModel",
                    "Searching with query: ${params.query}, tag: ${params.tag}, category: ${params.category}, countries: ${params.countries}"
                )

                Pager(
                    config = PagingConfig(pageSize = 10, enablePlaceholders = false,
                        initialLoadSize = 10

                    ),
                    pagingSourceFactory = {
                        SearchDataSource(
                            repository = repository,
                            tagName = params.tag,
                            categoryName = params.category,
                            countries = params.countries,
                            name = params.query,
                            size = 10

                        )
                    }
                ).flow.cachedIn(viewModelScope)
            }
        }
        .onEach {
            Log.d("SearchViewModel", "New PagingData emitted.")

        }

      //  .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())






























    private val _moviesFlow1 = MutableStateFlow<PagingData<MovieResult.DataMovie.Item>>(PagingData.empty())
  //  val moviesFlow1: StateFlow<PagingData<MovieResult.DataMovie.Item>> = _moviesFlow1.asStateFlow()


    @OptIn(FlowPreview::class)
    val moviesFlow12 = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_moviesFlow1) { text, product ->
            if(text.isBlank()) {
                product
            } else {
                delay(2000L)
                product.filter {

                    it.name?.contains(text) ?: false



                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _moviesFlow1.value
        )




























    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val moviesFlow1 = searchText
        .debounce(500L)
        .onEach { _isSearching.value=true }

        // ✅ Wait for user to stop typing
       // .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty()) // ✅ Return empty data when query is blank
            } else {
                Pager(
                    config = PagingConfig(
                        pageSize = 18, // ✅ Page size
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        SearchDataSource(
                            repository,
                            tagName = "",
                            categoryName = emptyList(),
                            countries = "",
                            name = query, // ✅ Pass search query to API
                            //size = 20
                        )
                    }
                ).flow.cachedIn(viewModelScope) // ✅ Convert Pager to Flow & cache it
            }
        }
        .onEach { _isSearching.value = false } // ✅ Hide loading after getting data
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagingData.empty()
        )






fun getMovieFlow(tags:String):StateFlow<PagingData<MovieResult.DataMovie.Item>>
{

    val moviesFlow1 = searchText
        .debounce(500L)
        .onEach { _isSearching.value=true }

        // ✅ Wait for user to stop typing
        // .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty()) // ✅ Return empty data when query is blank
            } else {
                Pager(
                    config = PagingConfig(
                        pageSize = 20, // ✅ Page size
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        SearchDataSource(
                            repository,
                            tagName = tags,
                            categoryName = emptyList(),
                            countries = "",
                            name = query, // ✅ Pass search query to API
                            //size = 20
                        )
                    }
                ).flow.cachedIn(viewModelScope) // ✅ Convert Pager to Flow & cache it
            }
        }
        .onEach { _isSearching.value = false } // ✅ Hide loading after getting data
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagingData.empty()
        )

    return moviesFlow1





















}







    fun onSearchTextChange(text: String) {
       _searchText.value = text
      /* _searchParams.value=SearchParams(
           category = "MOVIES"



       )*/
      //  _searchParams.value =


    }
























    fun getMovies(tag: String,categoryName:List<String>,countries:String,name:String,size:Int): Flow<PagingData<MovieResult.DataMovie.Item>> {
        Log.d(NOBINO_LOG_TAG,"getmovie......")
        Log.d(NOBINO_LOG_TAG, "tag is......${tag.toString()}")

        return Pager(
            config = PagingConfig(
                pageSize =20, // Page size
                enablePlaceholders = false
            ),


            pagingSourceFactory = {

                Log.d(NOBINO_LOG_TAG, "Creating new MoviePagingSource with categoryId: ${tag.toString()}")

                SearchDataSource(repository, tagName =tag,categoryName= categoryName, countries =countries, name = name )


            }
        ).flow.cachedIn(viewModelScope)
    }





    fun fetchCountries() {
        viewModelScope.launch {
            _isLoading.value = true
            val data = repository.getCountries()
            _countries.value = data
            _isLoading.value = false
        }
    }




}