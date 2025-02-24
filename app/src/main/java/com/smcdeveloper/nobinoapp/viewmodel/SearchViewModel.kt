package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.search.Countries
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

    private val _countries = MutableStateFlow<NetworkResult<Countries>>(NetworkResult.Loading())
    val contries: StateFlow<NetworkResult<Countries>> get() = _countries.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()


    private val _moviesFlow1 = MutableStateFlow<PagingData<MovieResult.DataMovie.Item>>(PagingData.empty())
    //val moviesFlow1: StateFlow<PagingData<MovieResult.DataMovie.Item>> = _moviesFlow1.asStateFlow()


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
        .debounce(500L) // ✅ Wait for user to stop typing
        .distinctUntilChanged()
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
                            tagName = "",
                            categoryName = "",
                            countries = "",
                            name = query, // ✅ Pass search query to API
                            size = 20
                        )
                    }
                ).flow.cachedIn(viewModelScope) // ✅ Convert Pager to Flow & cache it
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PagingData.empty()
        )














    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
























    fun getMovies(tag: String,categoryName:String,countries:String,name:String,size:Int): Flow<PagingData<MovieResult.DataMovie.Item>> {
        Log.d(NOBINO_LOG_TAG,"getmovie......")
        Log.d(NOBINO_LOG_TAG, "tag is......${tag.toString()}")

        return Pager(
            config = PagingConfig(
                pageSize =20, // Page size
                enablePlaceholders = false
            ),


            pagingSourceFactory = {

                Log.d(NOBINO_LOG_TAG, "Creating new MoviePagingSource with categoryId: ${tag.toString()}")

                SearchDataSource(repository, tagName =tag,categoryName= categoryName, countries =countries, name = name, size = size )


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