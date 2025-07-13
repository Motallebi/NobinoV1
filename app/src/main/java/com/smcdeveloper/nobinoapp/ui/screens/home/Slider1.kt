package com.smcdeveloper.nobinoapp.ui.screens.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel


class Slider1 {

    @Composable
    fun getSlider(viewModel: HomeViewModel = hiltViewModel())
    {

        val sliderState by viewModel.slider.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getSlider()
        }

        when (sliderState) {
            is NetworkResult.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is NetworkResult.Error -> {
                val errorMessage = (sliderState as NetworkResult.Error).message
                Text(
                    text = "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxSize(),
                    style = MaterialTheme.typography.nobinoMedium
                )
            }
            is NetworkResult.Success -> {
                val sliderInfo = (sliderState as NetworkResult.Success).data?.sliderData
                SliderList(sliderInfo ?: emptyList())
            }
        }
    }

    private @Composable
    fun CircularProgressIndicator() {
        TODO("Not yet implemented")
    }

    @Composable
    fun SliderList(sliderInfoList: List<Slider.Sliderinfo?>) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sliderInfoList.size) { index ->
                val sliderInfo = sliderInfoList[index]
                SliderItem(sliderInfo)
            }
        }
    }

    @Composable
    fun SliderItem(sliderInfo: Slider.Sliderinfo?) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
        ) {
            Column {
                val imagePath = sliderInfo?.imageHorizontalPath.orEmpty()
                AsyncImage(
                    model = imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = sliderInfo?.product?.name.orEmpty(),
                    style = MaterialTheme.typography.nobinoMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }









}