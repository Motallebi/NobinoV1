package com.smcdeveloper.nobinoapp.ui.screens.Product

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult

import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@Composable
fun MovieScreen(viewModel: HomeViewModel = hiltViewModel(), tagIds: List<Int>) {

    // val movieResults by viewModel.movieResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val movieDisplayData by viewModel.movieDisplayData.collectAsState()


    var movieList by remember {
        mutableStateOf<List<MovieResult.DataMovie.Item>>(emptyList())
    }




    // Trigger API call when the screen loads
    LaunchedEffect(tagIds) {
        viewModel.fetchMovieDisplayData(tagIds)
    }
    when {
        isLoading -> {
            // Show a loading indicator while data is being fetched
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        movieDisplayData.isNullOrEmpty() -> {
            // Show a message if no data is available
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No movie data available")
            }
        }
        else -> {
            // Pass the loaded data to LazyColumn
            val data1 = movieDisplayData!![0].movieItems

            Log.d("test1","size"+data1.size)


          //  Log.d("test1",data1.toString())
            MovieItemsLazyColumn(data1)
        }
    }













  /*  if (isLoading) {
        Log.d(LOG_TAG, "Loading---------------")

    } else if (movieDisplayData.isEmpty()) {
        Log.d(LOG_TAG, "---------------ListEmpty ")

    } */
   /* else {

        movieList = (movieDisplayData.get(0).movieItems ?: emptyList()) as List<MovieResult.DataMovie.Item>

        val data1 = movieDisplayData.get(0).movieItems
        val data2 = movieDisplayData.get(1).movieItems
        val data3 = movieDisplayData.get(2).movieItems


        Column(modifier = Modifier.fillMaxSize()) {
            if (data1.isEmpty()) {
                Text(
                    "No movie items available",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            else {

                Log.d("test1","data 1 size is "+data1.size.toString())
                LazyColumn {
                   items(data1.size){
                       MovieItem(data1.get(it)?.name.toString())


                   }


                }



              *//*  data1.forEach { movieItem ->
                    MovieItem(movieItem?.name.toString())*//*


            }
        }








     *//*  data1.forEach {

            MovieItem(it?.name.toString())


        }*//*






        *//* LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(movies, key = { it?.id!! }) { movie ->

                    Text(movie?.name.toString())


                    // MovieItem(movie)
                    }*//*


        //  Text(movieData?.name.toString())


        Log.d(LOG_TAG, "---------------DataHasArrived")

        Log.d(LOG_TAG, "-------cat------" + movieDisplayData.get(0).movieCat.title)
        movieDisplayData.get(0).movieItems.forEach {


            Log.d(LOG_TAG, "-------name------" + it?.name.toString())
            Log.d(LOG_TAG, "-------name------" + it?.translatedName.toString())
        }










        movieDisplayData.get(1).movieItems.forEach {

            Log.d(LOG_TAG, "-------name------" + it?.name.toString())
        }


        *//*   Box(modifier = Modifier.fillMaxSize()) {
            when {
                movieDisplayData.isEmpty() -> {
                    Text("No data available or loading...", modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(movieDisplayData, key = { it.movieCat.id ?: 0 }) { displayData ->
                            displayData?.let {
                                Text("Category: ${it.movieCat.title ?: "No Title"}")
                                Text("MovieResult: ${it.movieResult.toString()}")

                                it.movieItems.forEach { item ->
                                    Text("Movie Item: ${item?.name ?: "No Name"}")
                                }
                            }
                        }
                    }
                }
            }
        }
*//*


        *//*  LazyColumn {
            items(movieDisplayData) { displayData ->
                displayData?.let {
                    Text("Category: ${it.movieCat.title ?: "No Title"}")
                    Text("MovieResult: ${it.movieResult.toString()}")

                    it.movieItems.forEach { item ->
                        Text("Movie Item: ${item?.name ?: "No Name"}")
                    }
                }
            }
        }*//*


        // Log.d(LOG_TAG, movieDisplayData[2].movieResults.size.toString())

        *//*  LazyColumn {
            items(movieDisplayData) { displayData ->
                // Display MovieCat data
                Text("Category: ${displayData.movieCat.title}")

                // Display associated MovieResult
             //   Text("MovieResult: ${displayData.movieResult}")

                // Display movie items
             *//**//*   displayData.movieItems.forEach { item ->
                    Text("Movie Item: ${item?.name}")
                }*//**//*
            }
        }*//*


        *//*
    if (isLoading) {
        Text("Loading...") // Replace with a proper loading indicator
    } else if (movieDisplayData.isEmpty()) {
        Text("No data available") // Show this if the list remains empty
    } else {
        LazyColumn {
            items(movieDisplayData) { displayData ->
                Text("Category: ${displayData.movieCat.title}")
                displayData.movieResults.forEach { item ->
                    Text("Movie: ${item.toString()}")
                }
            }
        }
    }*//*


        *//*  Log.d(LOG_TAG,"SIZE----${movieDisplayData.size}")
    if (movieDisplayData.isNotEmpty()) {
        Log.d(LOG_TAG, movieDisplayData[0].movieResults.toString())
    } else {
        Log.d(LOG_TAG, "movieDisplayData is empty")
    }*//*
        // Log.d(LOG_TAG,movieDisplayData.get(0).movieResults.toString())


        // Log.d(LOG_TAG,movieDisplayData.get(1).movieResults.toString())
        // Log.d(LOG_TAG,movieDisplayData.get(1).movieResults.toString())


        *//* movieDisplayData.forEach { displaydata->
      val tag=  displaydata.movieCat.tags.toString()

        Log.d(LOG_TAG,"-----0000-----"+tag)
       val movieresult= displaydata.movieResults

       movieresult.forEach{ movie->

            movie.movieInfo?.items?.forEach {  singlemovie->


               Text("-----4444-----"+singlemovie?.name.toString())
                Log.d(LOG_TAG,"-----5555-----"+singlemovie?.name.toString())





            }


        }










    }*//*


        *//* LazyColumn {
        items(movieDisplayData) { displayData ->
            // Display MovieCat data
            val movieCat = displayData.movieCat
            Text("Category: ${movieCat.title}")
            Text("Tags: ${movieCat.tags?.joinToString(", ") ?: "No Tags"}")

            // Display associated MovieResults
            displayData.movieResults.forEach { movieResult ->
                Text("Movie: ${movieResult.toString()}")
            }
        }
    }
*//*


    }*/


}


    // Render UI based on the results
    /*when {
        movieResults.isEmpty() -> Text("Loading...")
        else -> LazyColumn {
            items(movieResults)  { result ->
                when (result) {
                    is NetworkResult.Loading()-> Text("Loading...")
                    is NetworkResult.Success -> {
                        val movieResult = result.data
                        Text("Movie: ${movieResult?.toString() ?: "No Data"}")
                    }
                    is NetworkResult.Error -> Text("Error: ${result.message}")
                }
            }
        }
    }*/





    @Composable
    fun test(viewModel: HomeViewModel = hiltViewModel()) {


    }


    @Composable
    fun MovieItem(test: String) {
        Text(test)
    }

@Composable
fun MovieItemsLazyColumn(data1: List<MovieResult.DataMovie.Item?>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        Log.d("test1","size"+data1.size)

        items(data1.size){ it->
            Text(data1.get(it)?.name.toString())

        }



    }
}
