package com.smcdeveloper.nobinoapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor():ViewModel()





{


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

















}