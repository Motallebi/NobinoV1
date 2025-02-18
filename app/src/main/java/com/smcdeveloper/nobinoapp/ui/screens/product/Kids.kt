package com.smcdeveloper.nobinoapp.ui.screens.product



import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.KidsBannerPics

import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRow
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRowBySection2
import com.smcdeveloper.nobinoapp.ui.screens.home.AnimatedImageSlider
import com.smcdeveloper.nobinoapp.ui.screens.home.SliderItemByTags
import com.smcdeveloper.nobinoapp.ui.theme.kidsPageColor
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import com.smcdeveloper.nobinoapp.viewmodel.KidsViewModel

@Composable
fun KidsMovieScreen(viewModel: KidsViewModel, navController: NavHostController,tags:List<Int>) {
    val sliderState by viewModel.slider2.collectAsState()
    val movieDataState by viewModel.movieDisplayData2.collectAsState()
  //  val delimiterState by viewModel.delimiter.collectAsState()
    val kidsBanner by viewModel.kidsBanner.collectAsState()
    val kidsBannerPics by viewModel.kidsBannerPics.collectAsState()





    LaunchedEffect(true) {
        viewModel.fetchAllDataforKids(tags)
    }



   // GetSlider()





    Box(
        Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.kidsPageColor)



    )
    {

        Image(
            painter = painterResource(id = R.drawable.kids), // Replace with your drawable
            contentDescription = "Background",
            contentScale = ContentScale.Crop, // Ensures it covers the entire screen
            modifier = Modifier.fillMaxSize()
        )


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            when {
                // Show loading state if either slider or movies are still loading
                     sliderState is NetworkResult.Loading ||
                        movieDataState is NetworkResult.Loading ||
                        kidsBanner is NetworkResult.Loading ||
                       kidsBannerPics is NetworkResult.Loading  -> {
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
                        kidsBanner is NetworkResult.Error ||
                        kidsBannerPics is NetworkResult.Error -> {
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
                        kidsBanner is NetworkResult.Success &&
                        kidsBannerPics is NetworkResult.Success ->


                {

                    Log.d("slider","movie3"+sliderState.data?.data.toString())
                    //   Log.d("slider2","movie3"+delimiterState.data?.imageData?.leftImageLink)




                    val sliderData = (sliderState as NetworkResult.Success).data?.data

                    val movies = (movieDataState as NetworkResult.Success).data ?: emptyList()

                    val banerPics = (kidsBannerPics as NetworkResult.Success).data





                   // val relatedProducts = (relatedProductsState as NetworkResult.Success).data?.movieInfo?.items ?: emptyList()

                    // Show the slider only once








                    // Show movies after the slider
                    movies.forEachIndexed {  index, displayData ->
                        item {
                            displayData.movieCat.tags?.let {
                                NobinoSpecialRowBySection2(
                                    displayData.movieCat.title.toString(),
                                    navController = navController,
                                    tags = it.filterNotNull() ,
                                    category = ""


                                )
                            }

                            //  TagHeader(tag = displayData.movieCat.title ?: "Unknown Category")
                        }

                        if(index==3)
                        {

                            item{


                                banerPics?.let { ImageGridScreen(it) }







                            }





/*
                            val size= relatedProducts.size

                            val images= listOf(
                                productState.data?.bannerData?.get(0)?.imagePath.toString(),

                                relatedProducts.get(size-1)?.images?.get(0)?.src.toString(),
                                relatedProducts.get(size-2)?.images?.get(0)?.src.toString(),

                                )*/







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




                          //  item { SpecialRow(images.toList()) }










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



@Composable
fun ImageCard(imagePath: String,modifier: Modifier) {
    Card(
        modifier = modifier
            .aspectRatio(1.6f) // Keep aspect ratio for card shape
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        AsyncImage(
            model = IMAGE_BASE_URL+imagePath ,
           // painter = painterResource(id = imageRes),
            contentDescription = "Grid Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun ImageGridScreen(bannerPics:KidsBannerPics) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD1C5A1)) // Beige background
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {

        val size = bannerPics.bannerData?.size


        for (i in 1..size!! step 2 ) {
            Log.d("size","i is $i")
            Log.d("size","i+1 is ${i+1}")
            Log.d("size","i-1 is ${i-1}")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            )

            {


                ImageCard(bannerPics.bannerData[i-1]?.imagePath.toString(), Modifier.weight(1f))

                // Check if there's a second image in the pair
                if (i  < size) {
                    ImageCard(bannerPics.bannerData[i]?.imagePath.toString(), Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.weight(1f)) // Keep grid balanced
                }
            }
        }









    }










}









