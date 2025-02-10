package com.smcdeveloper.nobinoapp.ui.screens.product



import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRow
import com.smcdeveloper.nobinoapp.ui.screens.home.AnimatedImageSlider
import com.smcdeveloper.nobinoapp.ui.screens.home.SliderItemByTags
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@Composable
fun MovieScreen(viewModel: HomeViewModel, navController: NavHostController,tags:List<Int>) {
    val sliderState by viewModel.slider2.collectAsState()
    val movieDataState by viewModel.movieDisplayData2.collectAsState()


    LaunchedEffect(true) {
        viewModel.fetchAllData(tags)
    }



   // GetSlider()





    LazyColumn(modifier = Modifier.fillMaxSize()) {
        when {
            // Show loading state if either slider or movies are still loading
            sliderState is NetworkResult.Loading || movieDataState is NetworkResult.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // Show error state if either request fails
            sliderState is NetworkResult.Error || movieDataState is NetworkResult.Error -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Failed to load data. Please try again.", color = Color.Red)
                    }
                }
            }

            // Show UI when both slider and movie data are ready
            sliderState is NetworkResult.Success && movieDataState is NetworkResult.Success -> {

                Log.d("slider","movie3"+sliderState.data?.data.toString())


                val sliderData = (sliderState as NetworkResult.Success).data?.data

                val movies = (movieDataState as NetworkResult.Success).data ?: emptyList()

                // Show the slider only once






                item {
                    if (sliderData != null) {
                        Log.d("slider","not null"+sliderState.data?.data.toString())
                        AnimatedImageSlider(sliderData)
                        Log.d("slider","not null")
                    }
                }

                // Show movies after the slider
                movies.forEach { displayData ->
                    item {
                        NobinoSpecialRow(
                            displayData.movieCat.title.toString(),
                            navController = navController,
                            displayData.movieCat,
                            ""
                        )

                        TagHeader(tag = displayData.movieCat.title ?: "Unknown Category")
                    }

                    // LazyRow for movie items
                    item {
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(displayData.movieItems) { datamovie ->
                                datamovie?.let {
                                    SliderItemByTags(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}