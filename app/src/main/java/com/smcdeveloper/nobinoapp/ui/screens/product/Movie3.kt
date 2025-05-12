package com.smcdeveloper.nobinoapp.ui.screens.product



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRow
import com.smcdeveloper.nobinoapp.ui.screens.home.CustomSlider
import com.smcdeveloper.nobinoapp.ui.screens.home.SliderItemByTags
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@Composable
fun MovieScreenHome(viewModel: HomeViewModel, navController: NavHostController, tags:List<Int>) {
    val sliderState by viewModel.slider2.collectAsState()
    val movieDataState by viewModel.movieDisplayData2.collectAsState()

    val productState by viewModel.product.collectAsState()
    val relatedProductsState by viewModel.relatedProducts.collectAsState()



    LaunchedEffect(true) {
        viewModel.fetchAllDataforKids(tags)
        viewModel.resetBackground()
    }


    // GetSlider()



        LazyColumn(modifier = Modifier.fillMaxSize()) {


            when {
                // Show loading state if either slider or movies are still loading
                sliderState is NetworkResult.Loading ||
                        movieDataState is NetworkResult.Loading ||
                        productState is NetworkResult.Loading ||
                        relatedProductsState is NetworkResult.Loading -> {


                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.Red)
                        }
                    }
                }





                // Show error state if either request fails
                sliderState is NetworkResult.Error ||
                        movieDataState is NetworkResult.Error ||
                        productState is NetworkResult.Error ||
                        relatedProductsState is NetworkResult.Error -> {
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
                sliderState is NetworkResult.Success &&
                        movieDataState is NetworkResult.Success &&
                        productState is NetworkResult.Success &&
                        relatedProductsState is NetworkResult.Success -> {




                    val sliderData = (sliderState as NetworkResult.Success).data?.data

                    val movies = (movieDataState as NetworkResult.Success).data ?: emptyList()

                    val relatedProducts =
                        (relatedProductsState as NetworkResult.Success).data?.movieInfo?.items
                            ?: emptyList()

                    // Show the slider only once


                    item {
                        if (sliderData != null) {

                          // AnimatedImageSlider(sliderData)
                            CustomSlider(modifier = Modifier,sliderData,)


                        }
                    }

                    // Show movies after the slider
                    movies.forEachIndexed { index, displayData ->
                        item {
                            NobinoSpecialRow(
                                displayData.movieCat.title.toString(),
                                navController = navController,
                                displayData.movieCat,

                                category = "",
                                categoryName = displayData.movieCat.title.toString(),
                                modifier = Modifier
                            )


                        }

                        if (index == 3) {


                            val size = relatedProducts.size

                            val images = listOf(
                                productState.data?.bannerData?.get(0)?.imagePath.toString(),

                                relatedProducts.get(size - 1)?.images?.get(0)?.src.toString(),
                                relatedProducts.get(size - 2)?.images?.get(0)?.src.toString(),

                                )







                            item {
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(16.dp),
                                    contentPadding = PaddingValues(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)


                                ) {
                                    items(displayData.movieItems) { datamovie ->
                                        datamovie?.let {
                                            SliderItemByTags(it,navController)
                                        }
                                    }
                                }
                            }




                            item { SpecialRow(images.toList()) }


                        }


                        // LazyRow for movie items
                        item {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(16.dp),
                                contentPadding = PaddingValues(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)


                            ) {
                                items(displayData.movieItems) { datamovie ->
                                    datamovie?.let {
                                        SliderItemByTags(it,navController)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }














}






