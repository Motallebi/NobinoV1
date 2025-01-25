package com.smcdeveloper.nobinoapp.ui.screens.product

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRow
import com.smcdeveloper.nobinoapp.ui.screens.home.SliderItemByTags
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.util.MovieDisplayData

import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@Composable
fun MovieScreen1(viewModel: HomeViewModel = hiltViewModel(), tagIds: List<Int>,
     navController:  NavHostController


) {

    // val movieResults by viewModel.movieResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val movieDisplayData by viewModel.movieDisplayData.collectAsState()
    LaunchedEffect(tagIds) {
        viewModel.fetchMovieDisplayData(tagIds)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                // Show loading indicator
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            movieDisplayData!!.isEmpty() -> {
                // Show no data message
                Text(
                    "No movie data available",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                // Show movie list grouped by tags
                MovieListByTag(movieDisplayData!!,navController)
            }
        }
    }









}


@Composable
fun  MovieListByTag(movieDisplayData: List<MovieDisplayData>,navController: NavHostController) {
    LazyColumn  (modifier = Modifier.fillMaxSize()) {
        movieDisplayData.forEach { displayData ->
            // Add tag header
            item {

                Log.d("category","category is...."+displayData.movieItems.get(0)?.category.toString())
                NobinoSpecialRow(displayData.movieCat.title.toString(), navController =navController,displayData.movieCat,
                   // displayData.movieItems.get(0)?.category.toString()
                    ""


                    )

                TagHeader(tag = displayData.movieCat.title ?: "Unknown Category")
            }

            item {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(displayData.movieItems) { datamovie ->
                        datamovie?.let {
                            SliderItemByTags(it) // Render individual movie item
                        }
                    }
                }
            }
        }
    }






            // Add movies for this tag


            }




@Composable
fun TagHeader(tag: String) {
    Text(
        text = tag,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(16.dp)
    )
}

@Composable
fun MovieItem(movieItem: MovieResult.DataMovie.Item) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = movieItem.name ?: "Unknown Movie", style = MaterialTheme.typography.nobinoMedium)
        Text(text = movieItem.name.toString() ?: "No Description Available")
    }
}