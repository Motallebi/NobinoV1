package com.smcdeveloper.nobinoapp.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.component.MovieCard
import com.smcdeveloper.nobinoapp.ui.component.MovieCard1
import com.smcdeveloper.nobinoapp.ui.component.MovieCardtest
import com.smcdeveloper.nobinoapp.ui.component.MovieCardtestByTag
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.util.Constants.LOG_TAG
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

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
            val sliderInfo = (sliderState as NetworkResult.Success).data?.data
            SliderList(sliderInfo ?: emptyList())
        }
    }
}







@Composable
fun SliderList(sliderInfoList: List<Slider.Sliderinfo?>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sliderInfoList.size) { index ->
            val sliderInfo = sliderInfoList[index]
            Log.d(LOG_TAG,"Slider PAth${sliderInfo?.imageHorizontalPath}")
            SliderItem1(sliderInfo)
        }
    }
}


@Composable
fun ShowSpecialCat(specialCat:String)
{
    Text(specialCat,
        style = MaterialTheme.typography.nobinoMedium




    )

}



@Composable
fun SliderByMoveTag(items: List<MovieResult.DataMovie.Item>) {


    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size) { index ->
            val movieInfo = items[index]

           // Log.d(LOG_TAG,"Slider PAth${sliderInfo?.imageHorizontalPath}")
            SliderItemByTags(movieInfo)
        }
    }
}





@Composable
fun SliderItem(sliderInfo: Slider.Sliderinfo?) {


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center





    )
    {
        Box()
        {



            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(300.dp)
            )
            {
                Column {
                    val imagePath = "https://vod.nobino.ir/vod/"+sliderInfo?.imageHorizontalPath.orEmpty()
                    Log.d(LOG_TAG,"Slider PAth-->:${imagePath}")

                    AsyncImage(
                        model = imagePath,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
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

























}

@Composable
fun SliderItem1(sliderInfo: Slider.Sliderinfo?) {

    MovieCardtest(sliderInfo)








    }

@Composable
fun SliderItemByTags(movieInfo: MovieResult.DataMovie.Item) {

    MovieCardtestByTag(movieInfo)








}






@Composable
fun GetSliderByTag(viewModel: HomeViewModel = hiltViewModel())
{

    val movieState by viewModel.movieState.collectAsState()
    val tagState by viewModel.moviesByTags.collectAsState()
    val moviesByTagState by viewModel.moviesByTag.collectAsState()



    LaunchedEffect(Unit) {
      //  viewModel.fetchMovies()

    }

    when (movieState) {
        is NetworkResult.Loading -> {
            Log.d(LOG_TAG,"Loading......")

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is NetworkResult.Error -> {
            val errorMessage = (movieState as NetworkResult.Error).message
            Text(
                text = "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxSize(),
                style = MaterialTheme.typography.nobinoMedium
            )
        }
        is NetworkResult.Success -> {
            val movieInfo = (movieState as NetworkResult.Success<List<MovieResult.DataMovie.Item>>).data
            Log.d(LOG_TAG,"networkcall.......")
          //  Log.d(LOG_TAG,"networkcall......."+movieInfo.)



           // SliderItemByTags(MovieResult.DataMovie?.Item: emptyList())


            movieInfo?.let {
                ShowSpecialCat(tagState.data?.movieCatData?.title.toString())
                ShowSpecialCat("..test..")


                SliderByMoveTag(it)

            }

           /* if (movieInfo != null) {
                SliderItemByTags(movieInfo.get(0))
            }*/
        }
    }























}


@Composable
fun GetSliderByTag2(viewModel: HomeViewModel = hiltViewModel()) {

   // val movieState by viewModel.movieState.collectAsState()
    //val tagState by viewModel.moviesByTags.collectAsState()
    val moviesByTagState by viewModel.moviesByTag.collectAsState()



    LaunchedEffect(Unit) {
        //  viewModel.fetchMovies()

    }

    moviesByTagState.forEach { (tag, result) ->
        Log.d(LOG_TAG,"movie tag is $tag")


        when (result) {

            is NetworkResult.Loading -> {
                Log.d(LOG_TAG, "Loading......")

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is NetworkResult.Error -> {
                val errorMessage = (result as NetworkResult.Error).message
                Text(
                    text = "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxSize(),
                    style = MaterialTheme.typography.nobinoMedium
                )
            }

            is NetworkResult.Success -> {
                val movieInfo = result.data

                // val movieInfo = (movieState as NetworkResult.Success<List<MovieResult.DataMovie.Item>>).data
                Log.d(LOG_TAG, "networkcall.......")
                Log.d(LOG_TAG, "networkcall......."+movieInfo.toString())
                if (movieInfo != null) {
                    SliderByMoveTag(movieInfo)
                }

                //  Log.d(LOG_TAG,"networkcall......."+movieInfo.)


                // SliderItemByTags(MovieResult.DataMovie?.Item: emptyList())


             /*   movieInfo?.let {
                  //  Log.d(LOG_TAG,it)
                  //  ShowSpecialCat(result.data?.movieCatData?.title.toString())
                    ShowSpecialCat("..test..")


                    SliderByMoveTag(it)

                }*/

                /* if (movieInfo != null) {
                 SliderItemByTags(movieInfo.get(0))
             }*/
            }
        }


    }
}


@Composable
fun GetSliderByTag3(viewModel: HomeViewModel = hiltViewModel()) {

    // val movieState by viewModel.movieState.collectAsState()
    //val tagState by viewModel.moviesByTags.collectAsState()
    val moviesByTagState by viewModel.moviesByTag.collectAsState()



    LaunchedEffect(Unit) {
        //  viewModel.fetchMovies()
        //   viewModel.fetchMoviesForTags()

    }



    LazyColumn {
        (1..10).forEach { tag ->
            Log.d(LOG_TAG, "9999999999999")

            item {
                Text("Tag: $tag", style = MaterialTheme.typography.nobinoLarge)







                when (val moviesResult = moviesByTagState[tag]) {
                    is NetworkResult.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        )
                        {
                            //  CircularProgressIndicator()
                        }






                        CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                    }

                    is NetworkResult.Success -> {
                        val movieInfo = moviesResult.data
                        if (movieInfo != null) {
                            SliderByMoveTag(movieInfo)
                        }


                    }

                    is NetworkResult.Error -> {}
                    null -> {}
                }
            }


        }
    }
}

@Composable
fun GetSliderByTag4(viewModel: HomeViewModel = hiltViewModel()) {

    // val movieState by viewModel.movieState.collectAsState()
    //val tagState by viewModel.moviesByTags.collectAsState()
   // val moviesByTagState by viewModel.moviesByTag.collectAsState()
    val moviesByParameterState by viewModel.moviesByParameter.collectAsState()


    LaunchedEffect(Unit) {
        //  viewModel.fetchMovies()
        //   viewModel.fetchMoviesForTags()

    }





        (1..10).forEach { tag ->
            Log.d(LOG_TAG, "9999999999999")


                Text("Tag: $tag", style = MaterialTheme.typography.nobinoLarge)







                when (val moviesResult = moviesByParameterState[tag]) {
                    is NetworkResult.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        )
                        {
                            //  CircularProgressIndicator()
                        }






                        CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                    }

                    is NetworkResult.Success -> {
                        val movieInfo = moviesResult.data
                        if (movieInfo != null) {

                            SliderByMoveTag(movieInfo)
                        }


                    }

                    is NetworkResult.Error -> {}
                    null -> {}
                }
            }





}





@Composable
fun GetSliderByTag5(viewModel: HomeViewModel = hiltViewModel()) {

    var loading by remember {

        mutableStateOf(true)

    }

    // val movieState by viewModel.movieState.collectAsState()
    //val tagState by viewModel.moviesByTags.collectAsState()
    // val moviesByTagState by viewModel.moviesByTag.collectAsState()
    val tagsAndMoviesState by viewModel.tagsAndMovies.collectAsState()


    LaunchedEffect(Unit) {
        //  viewModel.fetchMovies()
        //   viewModel.fetchMoviesForTags()



    }

    tagsAndMoviesState.forEach { (tag, result) ->
        // Button for the tag
      /*  Button(onClick = { *//* Add action if needed *//* }, modifier = Modifier.padding(8.dp)) {
            Text(text = "---"+tag)
            Text(text = "---"+tag)


        }*/



        when (result) {
            is NetworkResult.Loading -> {
                if(loading) {
                    CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                    Log.d(LOG_TAG, "Loading-------")
                }
            }
            is NetworkResult.Success -> {
                loading=false

                val movies = result.data
                if (movies != null) {
                    Text(text = "---"+tag.title)
                    Text(text = "---"+tag.id)
                    SliderByMoveTag(movies)
                }






            }

            is NetworkResult.Error -> {

                Log.d(LOG_TAG,result.message.toString())

            }
        }

    }
}












































