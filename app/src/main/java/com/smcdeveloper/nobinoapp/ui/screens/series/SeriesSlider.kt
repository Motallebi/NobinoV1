package com.smcdeveloper.nobinoapp.ui.screens.series

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.component.MovieCardtest
import com.smcdeveloper.nobinoapp.ui.component.SeriesCardtest
import com.smcdeveloper.nobinoapp.ui.screens.home.SliderItem1
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.util.Constants.LOG_TAG
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import com.smcdeveloper.nobinoapp.viewmodel.SeriesViewModel

@Composable
fun getSeriesSlider(viewModel: SeriesViewModel = hiltViewModel())
{

    val State by viewModel.series.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSeriestBySize()
    }

    when (State) {
        is NetworkResult.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is NetworkResult.Error -> {
            val errorMessage = (State as NetworkResult.Error).message
            Text(
                text = "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxSize(),
                style = MaterialTheme.typography.nobinoMedium
            )
        }
        is NetworkResult.Success -> {
            val seriesInfo = (State as NetworkResult.Success<MovieResult>).data
            val items = seriesInfo?.movieInfo?.items?.filterNotNull() ?: emptyList()

                SeriesList(items)
            }
        }
    }



@Composable
fun SeriesList(items: List<MovieResult.DataMovie.Item>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.size.let {
            items(it) { index ->
                val seriesInfo = items
                if (seriesInfo != null) {
                    //Log.d(LOG_TAG, "Slider PAth${seriesInfo.items?.get(index)}")
                }
                SeriesSliderItem (items[index])
            }
        }
    }
}


@Composable
fun SeriesSliderItem(item:MovieResult.DataMovie.Item) {

    SeriesCardtest(item)








}