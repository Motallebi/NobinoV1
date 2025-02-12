package com.smcdeveloper.nobinoapp.ui.screens.product



import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import coil3.compose.rememberAsyncImagePainter

import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRow
import com.smcdeveloper.nobinoapp.ui.screens.home.AnimatedImageSlider
import com.smcdeveloper.nobinoapp.ui.screens.home.SliderItemByTags
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@Composable
fun MovieScreen(viewModel: HomeViewModel, navController: NavHostController,tags:List<Int>) {
    val sliderState by viewModel.slider2.collectAsState()
    val movieDataState by viewModel.movieDisplayData2.collectAsState()
  //  val delimiterState by viewModel.delimiter.collectAsState()
    val productState by viewModel.product.collectAsState()
    val relatedProductsState by viewModel.relatedProducts.collectAsState()



    LaunchedEffect(true) {
        viewModel.fetchAllData(tags)
    }



   // GetSlider()





    LazyColumn(modifier = Modifier.fillMaxSize()) {
        when {
            // Show loading state if either slider or movies are still loading
            sliderState is NetworkResult.Loading ||
            movieDataState is NetworkResult.Loading ||
            productState is NetworkResult.Loading ||
            relatedProductsState is NetworkResult.Loading  -> {
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
                    relatedProductsState is NetworkResult.Success ->


                {

                Log.d("slider","movie3"+sliderState.data?.data.toString())
             //   Log.d("slider2","movie3"+delimiterState.data?.imageData?.leftImageLink)




                val sliderData = (sliderState as NetworkResult.Success).data?.data

                val movies = (movieDataState as NetworkResult.Success).data ?: emptyList()

                 val relatedProducts = (relatedProductsState as NetworkResult.Success).data?.movieInfo?.items ?: emptyList()

                // Show the slider only once






                item {
                    if (sliderData != null) {
                        Log.d("slider","not null"+sliderState.data?.data.toString())
                        AnimatedImageSlider(sliderData)
                        Log.d("slider","not null")
                    }
                }

                // Show movies after the slider
                movies.forEachIndexed {  index, displayData ->
                    item {
                        NobinoSpecialRow(
                            displayData.movieCat.title.toString(),
                            navController = navController,
                            displayData.movieCat,
                            ""
                        )

                      //  TagHeader(tag = displayData.movieCat.title ?: "Unknown Category")
                    }

                    if(index==3)
                    {


                        val size= relatedProducts.size

                        val images= listOf(
                            productState.data?.bannerData?.get(0)?.imagePath.toString(),

                            relatedProducts.get(size-1)?.images?.get(0)?.src.toString(),
                            relatedProducts.get(size-2)?.images?.get(0)?.src.toString(),

                        )







                        item {
                            LazyRow(modifier = Modifier.fillMaxWidth()
                                .padding(16.dp),
                                contentPadding = PaddingValues(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)



                            ) {
                                items(displayData.movieItems) { datamovie ->
                                    datamovie?.let {
                                        SliderItemByTags(it)
                                    }
                                }
                            }
                        }




                        item { SpecialRow(images.toList()) }










                    }






                    // LazyRow for movie items
                    item {
                        LazyRow(modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)



                        ) {
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



@Composable
fun SpecialRow(imageUrls: List<String?>) {
    if (imageUrls.size < 3) return // Ensure at least 3 images are provided

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Large Image on Top
        Image(
            painter = rememberAsyncImagePainter(model = IMAGE_BASE_URL+imageUrls[0]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f) // Adjust as needed
                .padding(bottom = 4.dp)
        )

        // Two Smaller Images Below in a Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 1..2) {
                Image(
                    painter = rememberAsyncImagePainter(model = IMAGE_BASE_URL+imageUrls[i]),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f) // Equal width for both images
                        .aspectRatio(16f / 9f) // Same aspect ratio as the top image
                        .padding(4.dp)
                )
            }
        }
    }
}










