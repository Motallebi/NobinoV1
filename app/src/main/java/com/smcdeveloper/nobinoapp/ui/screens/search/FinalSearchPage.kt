package com.smcdeveloper.nobinoapp.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel


@Composable
fun FinalPage(viewModel: FilterViewModel) {
    val filterCriteria = viewModel.filterCriteria.value

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.Black)
    ) {
        Text("Collected Data:", color = Color.White)
        Text("Genres: ${filterCriteria.selectedGenres}", color = Color.White)
        Text("Countries: ${filterCriteria.selectedCountries}", color = Color.White)
        Text("Years: ${filterCriteria.selectedYears}", color = Color.White)

        Button(
            onClick = {
                // Make the API Call Here
                performApiCall(filterCriteria)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Fetch Data")
        }
    }
}

fun performApiCall(filterCriteria: FilterCriteria) {
    // Example API call logic using Retrofit or any HTTP client
    println("Performing API call with: $filterCriteria")
}
