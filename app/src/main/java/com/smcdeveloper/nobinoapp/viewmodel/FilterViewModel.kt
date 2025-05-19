package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.search.Countries
import com.smcdeveloper.nobinoapp.data.model.search.GenreInfo
import com.smcdeveloper.nobinoapp.data.model.search.PersonInfo
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.FilterRepository
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterCriteria
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(private val filterRepository: FilterRepository) :
    ViewModel() {

    private val _isShowClearIconVisible = MutableStateFlow<Boolean>(false)
    val isShowClearIconVisible : StateFlow<Boolean> = _isShowClearIconVisible.asStateFlow()


    private val _filterType = MutableStateFlow<String>(FilterType.GENRE.name) // "genre", "city", "actor", "director"
    val filterType: StateFlow<String> = _filterType.asStateFlow()









    fun updateIconVisibility(showIcon:Boolean)
    {

        _isShowClearIconVisible.value=showIcon
        //  Log.d("log","viewModel login value is"+_isLoging.value)





    }





















    private val _actors = MutableStateFlow<NetworkResult<List<PersonInfo>>>(NetworkResult.Loading())
    val actors: StateFlow<NetworkResult<List<PersonInfo>>> get() = _actors.asStateFlow()


    private val _genres = MutableStateFlow<NetworkResult<List<GenreInfo>>>(NetworkResult.Loading())
    val genres: StateFlow<NetworkResult<List<GenreInfo>>> get() = _genres.asStateFlow()

    private val _checkBoxStates1 =  mutableStateMapOf<String,Boolean>()
    val checkBoxStates1: SnapshotStateMap<String, Boolean> = _checkBoxStates1


    private val _genreCheckBoxStates = MutableStateFlow<MutableMap<String, Boolean>>(mutableMapOf())
    val genreCheckBoxStates: StateFlow<Map<String, Boolean>> = _genreCheckBoxStates.asStateFlow()


    private val _countryCheckBoxStates = MutableStateFlow<MutableMap<String, Boolean>>(mutableMapOf())
    val countryCheckBoxStates: StateFlow<Map<String, Boolean>> = _countryCheckBoxStates.asStateFlow()

    // Keep track of whether each filter has any checked boxes
    private val _hasGenreChecked = MutableStateFlow(false)
    val hasGenreChecked: StateFlow<Boolean> = _hasGenreChecked.asStateFlow()

    private val _hasContryChecked = MutableStateFlow(false)
    val hasContryChecked: StateFlow<Boolean> = _hasContryChecked.asStateFlow()













    private val _countries = MutableStateFlow<NetworkResult<Countries>>(NetworkResult.Loading())
    val contries: StateFlow<NetworkResult<Countries>> get() = _countries.asStateFlow()












    fun updateCheckBoxSate(key:String,isChecked:Boolean)
    {

        _genreCheckBoxStates.value[key]=isChecked





    }

    fun updateCheckBoxState(key: String, isChecked: Boolean) {
        viewModelScope.launch {
            val currentStates = _genreCheckBoxStates.value.toMutableMap()
            currentStates[key] = isChecked
            _genreCheckBoxStates.value = currentStates.toMutableMap() // Create a *new* map!
        }
    }












    fun getCheckState(key:String):Boolean
    {
        Log.d("Filter3", "ViewModel $key")
        Log.d("Filter3" , "status is  ${_genreCheckBoxStates.value["Thriller"]}")

        return _genreCheckBoxStates.value.getOrDefault(key, false)


    }


    init {

        fetchGenres()
        fetchCountries()







    }













    var filterCriteria = mutableStateOf(FilterCriteria())
        private set

    // Update genre selection
    fun updateGenres(genres: Set<String>) {
        filterCriteria.value = filterCriteria.value.copy(selectedGenres = genres)
    }

    // Update country selection
    fun updateCountries(countries: Set<String>) {
        filterCriteria.value = filterCriteria.value.copy(selectedCountries = countries)
    }

    // Update year selection
    fun updateYears(years: Set<String>) {
        filterCriteria.value = filterCriteria.value.copy(selectedYears = years)
    }

    // Reset all filters
    fun resetFilters() {
        filterCriteria.value = FilterCriteria() // Reset to default empty state
    }

    // Get total filter count
    fun getFilterCount(): Int {
        return filterCriteria.value.run {
            selectedGenres.size + selectedCountries.size + selectedYears.size
        }
    }


    fun fetchActors(name: String) {
        viewModelScope.launch {
            _actors.value = NetworkResult.Loading()
            val data = filterRepository.fetchActors(name)

            // 🔴 Extract `personInfo` list only if API call is successful
            _actors.value = when (data) {
                is NetworkResult.Success -> NetworkResult.Success(data.data?.personInfo) // ✅ Extract actors


                is NetworkResult.Error -> NetworkResult.Error(data.message.toString()) // ❌ Handle API error
                is NetworkResult.Loading -> NetworkResult.Loading()


            }
        }
    }


    fun fetchGenres() {
        viewModelScope.launch {
            _genres.value = NetworkResult.Loading()
            val data = filterRepository.fetchGenres()
            Log.d("FilterViewModel", "API Response: $data") // ✅ Log API response

            // 🔴 Extract `personInfo` list only if API call is successful
            _genres.value = when (data) {
                is NetworkResult.Success -> {
                    Log.d(
                        "FilterViewModel",
                        "Raw API Data: ${data.data?.genreInfo}" + "SIZE IS =" + data.data?.genreInfo?.size

                    ) // ✅ Log Unfiltered Data


                    val filteredGenres = data.data?.genreInfo?.filter {

                        it.fixed && !it.invisible


                    }
                    Log.d(
                        "FilterViewModel",
                        "Filtered Genres: ${filteredGenres} Data Size IS : ${filteredGenres?.size}"
                    ) // ✅ Log Filtered Data

                    filteredGenres?.forEach {


                        updateGenreCheckBoxState(it.translatedName,false)


                    }




                    NetworkResult.Success(filteredGenres)



                }


                // ✅ Extract Gnres


                is NetworkResult.Error -> NetworkResult.Error(data.message.toString()) // ❌ Handle API error
                is NetworkResult.Loading -> NetworkResult.Loading()


            }
        }





    }





    fun fetchCountries() {
        viewModelScope.launch {
           // _isLoading.value = true
            val data = filterRepository.getCountries()
            _countries.value = data
            data.data?.countryInfo?.forEach { countryInfo ->

                updateCountryCheckBoxState(countryInfo.name,false)


            }





           // _isLoading.value = false
        }
    }












    fun clearAllGenreCheckBoxes(genres:List<GenreInfo>)
    {
        genres.forEach {

          updateCheckBoxSate(it.translatedName,false)







        }









    }










    fun onRemoveAllClick(filterType:FilterType) {
        if (filterType == FilterType.GENRE) {
            Log.d("OnRemoveAll", "Genre Filter Clicked")
            // Get the current list of genres and reset their checkbox states
            val currentGenres = (_genres.value as? NetworkResult.Success)?.data ?: emptyList()
            val updatedCheckBoxStates = _genreCheckBoxStates.value.toMutableMap()
            currentGenres.forEach { genre ->
                updatedCheckBoxStates[genre.translatedName.toString()] = false
                Log.d("OnRemoveAll", "Genre Filter Clicked ${genre.translatedName}")


            }
            _genreCheckBoxStates.value = updatedCheckBoxStates
            updateClearButtonVisibility()
            _hasGenreChecked.value = false
        }


        if (filterType == FilterType.COUNTRY) {
            Log.d("OnRemoveAll", "Genre Filter Clicked")
            // Get the current list of genres and reset their checkbox states
            val currentCountries = (_countries.value as? NetworkResult.Success)?.data?.countryInfo ?: emptyList()
            val updatedCheckBoxStates = _countryCheckBoxStates.value.toMutableMap()
            currentCountries.forEach { country ->
                updatedCheckBoxStates[country.name.toString()] = false
               // Log.d("OnRemoveAll", "Genre Filter Clicked ${genre.translatedName}")


            }
            _countryCheckBoxStates.value = updatedCheckBoxStates
            updateClearButtonVisibility()
            _hasContryChecked.value = false
        }

















        // Handle other FilterTypes if needed
    }


    // Set the currently displayed filter
    fun setCurrentFilter(filter: String) {
        _filterType.value = filter
        updateClearButtonVisibility() // Update visibility whenever the filter changes

    }


    // Update button visibility
    private fun updateClearButtonVisibility() {
        val currentFilterType = _filterType.value
        Log.d("currentfilter","current filter type is $currentFilterType")

        val isVisible = when (currentFilterType) {
            FilterType.GENRE.name-> _hasGenreChecked.value
            FilterType.COUNTRY.name -> _hasContryChecked.value
           // "actor" -> _actorCheckBoxStates.value.any { it.value }
          //  "director" -> _directorCheckBoxStates.value.any { it.value }
            else -> false
        }
        _isShowClearIconVisible.value = isVisible
    }



    // Update checkbox states and filter type
    fun updateGenreCheckBoxState(key: String, isChecked: Boolean) {
        viewModelScope.launch {
            val currentStates = _genreCheckBoxStates.value.toMutableMap()
            currentStates[key] = isChecked
            _genreCheckBoxStates.value = currentStates
            _filterType.value = FilterType.GENRE.name
            _hasGenreChecked.value = currentStates.any { it.value } // Update
            updateClearButtonVisibility()
        }
    }

    fun updateCountryCheckBoxState(key: String, isChecked: Boolean) {
        viewModelScope.launch {
            val currentStates = _countryCheckBoxStates.value.toMutableMap()
            currentStates[key] = isChecked
            _countryCheckBoxStates.value = currentStates
            _filterType.value = FilterType.COUNTRY.name
           _hasContryChecked.value = currentStates.any { it.value } // Update
            updateClearButtonVisibility()
        }
    }











    fun getAllData()
    {









    }







}