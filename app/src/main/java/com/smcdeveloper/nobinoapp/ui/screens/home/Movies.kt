package com.smcdeveloper.nobinoapp.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import com.smcdeveloper.nobinoapp.util.Constants.LOG_TAG_IMAGES
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun getAllMovies(viewModel: HomeViewModel = hiltViewModel())
{

    var movieList by remember {

        mutableStateOf<List<MovieResult.DataMovie.Item?>>(emptyList())


    }


    var movieResult by remember {
        mutableStateOf<MovieResult?>(null)
    }







    val results by viewModel.productsListBySize.collectAsState()
       when(results)
       {
           is NetworkResult.Success -> {

               //MovieImagesList(movieResult)
               MovieImagesList2(movieResult)



               /* results.data?.movieInfo?.items.let { data->
                    if (data != null) {
                        movieList= data
                       // imageList=data[0]
                    }*/

               results.data.let {

                   movieResult=it

               }




               }





           is NetworkResult.Error -> {

           }
           is NetworkResult.Loading -> {

           }



       }




    Log.d("nobino","internet")


    LaunchedEffect(true) {

        viewModel.productsListBySize.collectLatest { result ->
            when (result) {
                is NetworkResult.Success -> {

                    result.data?.movieInfo.let { movieInfo->


                        Log.d(NOBINO_LOG_TAG, "Movie List Size ${movieInfo?.items?.size}")

                    }






                    result.data?.movieInfo?.items?.forEach {


                        Log.d(NOBINO_LOG_TAG, "Movie Name is${it?.name.toString()}")




                        Log.d(
                            NOBINO_LOG_TAG, "Movie Images.....${
                                it?.images?.forEach {

                                    Log.d(NOBINO_LOG_TAG, "Movie Name is${it?.imageType.toString()}")
                                    Log.d(NOBINO_LOG_TAG, "Movie Name is${it?.src.toString()}")


                                }
                            }"
                        )


                    }


                    //MovieResult

                    Log.d(NOBINO_LOG_TAG, "success")
                    Log.d(NOBINO_LOG_TAG, result.data.toString())


                    Log.d(NOBINO_LOG_TAG, "---------" + result.message.toString())
                    Log.d(NOBINO_LOG_TAG, "---88------" + result.message.toString())
                    Log.d(NOBINO_LOG_TAG, "---8877------" + result.message.toString())
                    Log.d(NOBINO_LOG_TAG, "---887888------" + result.message.toString())


                    Log.d(NOBINO_LOG_TAG, "success result" + result.data.toString())

                    Log.d(NOBINO_LOG_TAG, result.data.toString())


                }

                is NetworkResult.Error -> {
                    Log.d(NOBINO_LOG_TAG, "Error")
                    Log.d(NOBINO_LOG_TAG, result.message.toString())


                }

                is NetworkResult.Loading -> {
                    Log.d(NOBINO_LOG_TAG, "loading")

                }


            }


        }


    }

    //ShowMiveBox(movieList)

        // Pass the non-null movieResult to MovieImagesList







}




@Composable
fun ShowMiveBox(data:List<MovieResult.DataMovie.Item?>)
{
    var imageList by remember {

        mutableStateOf<String>("")



    }





    Box(
        modifier = Modifier
            .fillMaxSize(0.5f)
            .background(Color.Green),
        contentAlignment = Alignment.TopStart


    )
    {
        LazyColumn() {

            itemsIndexed(data)
            {
                  index,item->




              // Text("${index}---${item?.name.toString()}")
             //   FilmBox("${index}---${item?.name.toString()}")

            //   Log.d(LOG_TAG_IMAGES,"images....${item?.images?.get(0)?.imageType.toString()}")
               Log.d(LOG_TAG_IMAGES,"images....${item?.name.toString()}")
               item?.images?.forEach {

                   if (it != null) {
                      imageList= it.src.toString()
                   }



               }








               Log.d(LOG_TAG_IMAGES,"images...")
               Log.d(LOG_TAG_IMAGES,"images..."+index)




            //  FilmBox2(item?.images.)






         //    Spacer(modifier = Modifier.fillMaxWidth())





            }







        }




    }



}

@Composable
fun MovieImagesList(movieResult: MovieResult?) {
    // Extract images from the data class
    val images = movieResult?.movieInfo?.items
        ?.flatMap { it?.images?.mapNotNull { image -> image?.src } ?: emptyList() }
        ?: emptyList()

    //Log.d(LOG_TAG_IMAGES,"ImageList"+ movieResult?.movieInfo?.items?.get(0)?.images?.get(0)?.src.toString())
    Log.d(LOG_TAG_IMAGES,"ImageList"+ movieResult?.movieInfo?.
     items?.get(0)?.name.toString())

    // LazyColumn to display images
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { imageUrl ->
            MovieImageCard(imageUrl)
        }
    }
}

@Composable
fun MovieImageCard(imageUrl: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Use Coil's AsyncImage to load the movie image
        AsyncImage(
            model = imageUrl,
            contentDescription = "Movie Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun MovieImagesList2(movieResult: MovieResult?) {
    val items = movieResult?.movieInfo?.items ?: emptyList()
    val lazyListState = rememberLazyListState()
    val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex


    LazyColumn(state = lazyListState) {



        items(items) { item ->
            val imageUrl = "https://vod.nobino.ir/vod/"+item?.images?.firstOrNull()?.src
          //  val imageUrl = "https://vod.nobino.ir/vod/IMAGES/2024-8/9975/9975_1722932998427_IMAGES_POSTER.jpg"

            val movieName = item?.name ?: "Unknown Movie"
            val imdbRating = item?.popularityRate?.toString() ?: "N/A"

            // Log the image URL to Logcat
            Log.d(LOG_TAG_IMAGES, "Image URL: $imageUrl")

            if (imageUrl != null) {
                MovieImageCard(imageUrl =imageUrl, movieName = movieName, imdbRating = imdbRating)
                Log.d(LOG_TAG_IMAGES, "Image URL Complete: $imageUrl")
            }
            Text("MOVIE.........")
            Text("ITEM_INDEX=${firstVisibleItemIndex}")


        }
    }
}

@Composable
fun MovieImageCard(imageUrl: String, movieName: String, imdbRating: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray) // Fallback while loading
    ) {
        // Background image
        AsyncImage(
            model = imageUrl,
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 0f
                    )
                )
        )

        // Text overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = movieName,
                style = MaterialTheme.typography.nobinoMedium

                )


            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFFFD700)) // IMDb yellow color
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "IMDb $imdbRating",
                    style = MaterialTheme.typography.nobinoMedium
                )
            }
        }
    }
}










