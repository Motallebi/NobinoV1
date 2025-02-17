package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.model.prducts.Section
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import com.smcdeveloper.nobinoapp.data.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val repository: SeriesRepository
) : ViewModel() {

    // Holds the state of sections (e.g., titles with tags)
    private val _sections =
        MutableStateFlow<NetworkResult<List<Section.Data>>>(NetworkResult.Loading())
    val sections: StateFlow<NetworkResult<List<Section.Data>>> = _sections

    // Holds the movies grouped by section tags
    private val _moviesByTags =
        MutableStateFlow<NetworkResult<Map<String, List<MovieResult.DataMovie.Item>>>>(NetworkResult.Loading())
    val moviesByTags: StateFlow<NetworkResult<Map<String, List<MovieResult.DataMovie.Item>>>> = _moviesByTags


    private val _series = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val series: StateFlow<NetworkResult<MovieResult>> get() = _series.asStateFlow()

    private val _slider= MutableStateFlow<NetworkResult<Slider>>(NetworkResult.Loading())
    val slider: StateFlow<NetworkResult<Slider>> get() = _slider.asStateFlow()

    private val _isLoading = MutableStateFlow(false) // 🔴 Loading state
    val isLoading1: StateFlow<Boolean> = _isLoading.asStateFlow()






    /**
     * Fetch sections and update the `_sections` state.
     */


    fun fetchSectionTest(id: Int) {
        _sections.value = NetworkResult.Loading() // Set

        val result = repository.fetchSectionNames(id)


    }











    fun fetchMoviesForTags(tagsList: List<List<String>>) {
        viewModelScope.launch {
            _moviesByTags.value = NetworkResult.Loading() // Set loading state
            try {
                val moviesByTagsMap = mutableMapOf<String, List<MovieResult.DataMovie.Item>>()

                // Fetch movies for each tag set asynchronously
                tagsList.forEach { tags ->
                    val result = repository.fetchMoviesByTag(tags).first()
                    when (result) {
                        is NetworkResult.Success -> {
                            val items = result.data?.movieInfo?.items.orEmpty().filterNotNull()
                            moviesByTagsMap[tags.joinToString(",")] = items
                        }

                        is NetworkResult.Error -> {
                            Log.e("MOVIE_VIEWMODEL", "Error fetching movies for tags: $tags")
                        }

                        else -> Unit // Handle Loading or unexpected states if necessary
                    }
                }

                _moviesByTags.value = NetworkResult.Success(moviesByTagsMap)
                Log.d(
                    "MOVIE_VIEWMODEL",
                    "Movies fetched successfully for ${tagsList.size} tag groups."
                )
            } catch (e: Exception) {
                _moviesByTags.value = NetworkResult.Error("Failed to fetch movies: ${e.message}")
                Log.e("MOVIE_VIEWMODEL", "Error fetching movies: ${e.message}")
            }
        }
    }


    fun fetchSections(id: Int) {
        viewModelScope.launch {
            Log.d("MOVIE_VIEWMODEL", "Fetching sections for ID: $id")
            repository.fetchSectionNames(id).collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        Log.d("MOVIE_VIEWMODEL", "Loading sections...")
                        _sections.value = NetworkResult.Loading()
                    }
                    is NetworkResult.Success -> {
                        Log.d("MOVIE_VIEWMODEL", "Fetched sections successfully: ${result.data?.size} sections")
                        _sections.value = NetworkResult.Success(result.data.orEmpty())
                    }
                    is NetworkResult.Error -> {
                        Log.e("MOVIE_VIEWMODEL", "Error fetching sections: ${result.message}")
                        _sections.value = NetworkResult.Error(result.message ?: "Failed to fetch sections")
                    }
                }
            }
        }
    }





    fun fetchSections2(id: Int) {
            viewModelScope.launch {
                Log.d("MOVIE_VIEWMODEL", "Fetching sections for ID: $id")
                _sections.value = NetworkResult.Loading() // Set loading state

                val result = repository.fetchSectionNames(id).first()
                when (result) {
                    is NetworkResult.Success -> {
                        Log.d(
                            "MOVIE_VIEWMODEL",
                            "Fetched sections successfully: ${result.data?.size} sections"
                        )
                        _sections.value = NetworkResult.Success(result.data.orEmpty())
                    }

                    is NetworkResult.Error -> {
                        Log.e("MOVIE_VIEWMODEL", "Error fetching sections: ${result.message}")
                        _sections.value =
                            NetworkResult.Error(result.message ?: "Failed to fetch sections")
                    }

                    is NetworkResult.Loading -> {

                        Log.d("MOVIE_VIEWMODEL", "Loading.........")

                    }
                }
            }
        }

        /**
         * Fetch movies for all sections and group them by tags.
         */


        fun fetchAllMovies(sections: List<Section.Data>, size: String = "10", category: String = "") {
            viewModelScope.launch {
                Log.d("MOVIE_VIEWMODEL", "Starting to fetch movies for ${sections.size} sections")
                _moviesByTags.value = NetworkResult.Loading() // Set loading state

                val moviesBySection = mutableListOf<Pair<String, List<MovieResult.DataMovie.Item>>>()

                try {
                    // Perform all API calls asynchronously
                    val deferredResults = sections.map { section ->
                        async {
                            val sectionTitle = section.title
                            val tags = section.tags

                            if (tags.isEmpty()) {
                                Log.e("MOVIE_VIEWMODEL", "Tags are empty for section: $sectionTitle. Skipping...")
                                sectionTitle to emptyList<MovieResult.DataMovie.Item>()
                            } else {
                                Log.d("MOVIE_VIEWMODEL", "Fetching movies for section: $sectionTitle with tags: $tags")
                                var items: List<MovieResult.DataMovie.Item> = emptyList()

                                repository.fetchMoviesByTag(tags = tags).collect { result ->
                                    when (result) {
                                        is NetworkResult.Loading -> {
                                            Log.d("MOVIE_VIEWMODEL", "Loading movies for section: $sectionTitle...")
                                        }
                                        is NetworkResult.Success -> {
                                            items = result.data?.movieInfo?.items.orEmpty().filterNotNull()
                                            Log.d(
                                                "MOVIE_VIEWMODEL",
                                                "Successfully fetched movies for section: $sectionTitle. Movies count: ${items.size}"

                                            )

                                            Log.d(
                                                "MOVIE_VIEWMODEL",
                                                "Successfully fetched movies for section: $sectionTitle. Movies are: ${items.toString()}"

                                            )
                                        }
                                        is NetworkResult.Error -> {
                                            Log.e(
                                                "MOVIE_VIEWMODEL",
                                                "Error fetching movies for section: $sectionTitle. Message: ${result.message}"
                                            )
                                        }
                                    }
                                }

                                sectionTitle to items
                            }
                        }







                    }

                    // Await all results
                    val results = deferredResults.awaitAll()
                    Log.d("MOVIE_VIEWMODEL", "All movie fetch operations completed. Processing results...")

                    // Add all results to the list
                    moviesBySection.addAll(results)

                    // Convert to Map and emit the final state
                    _moviesByTags.value = NetworkResult.Success(moviesBySection.toMap())
                    Log.d("MOVIE_VIEWMODEL", "Movies grouped by sections successfully. Total sections: ${moviesBySection.size}")

                } catch (e: Exception) {
                    Log.e("MOVIE_VIEWMODEL", "Unexpected error during movie fetch: ${e.message}")
                    _moviesByTags.value = NetworkResult.Error("Failed to fetch movies for sections.")
                }
            }
        }







    fun getSeriestBySize() {

            viewModelScope.launch {
                //  seriesListBySize.emit(repository.getSeriesProductBysize())
                _series.value = NetworkResult.Loading()
                val result = repository.getSeriesProductBysize()
                _series.value = result

            }
        }





    fun getSlider() {

        viewModelScope.launch {
            _slider.value = NetworkResult.Loading()
            val result = repository.getSlider()
            _slider.value = result
            _isLoading.value = false


        }


    }













}


