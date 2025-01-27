package com.smcdeveloper.nobinoapp.ui.screens.training

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.component.NobinoSpecialRowBySection2
import com.smcdeveloper.nobinoapp.ui.screens.series.SliderItemByTags


import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import com.smcdeveloper.nobinoapp.viewmodel.SeriesViewModel

@Composable
fun TrainingScreen(navController: NavHostController) {


    Text("Learning.......")
    // SectionTest()

    SectionListScreen(navController = navController,
        onMovieClick = {}
    )


}


@Composable
fun SectionListScreen(
    viewModel: SeriesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit, // Callback for navigating to movie details
    navController: NavHostController
)
{
    val sectionsResult by viewModel.sections.collectAsState()
    val moviesByTagsResult by viewModel.moviesByTags.collectAsState()

    // Log the state of sectionsResult and moviesByTagsResult
    Log.d("SectionListScreen", "sectionsResult: $sectionsResult")
    Log.d("SectionListScreen", "moviesByTagsResult: $moviesByTagsResult")





    // Fetch sections when the screen loads
    LaunchedEffect(Unit) {
        Log.d("SectionListScreen", "Fetching sections...")
        viewModel.fetchSections(id = 41) // Replace with your actual section ID
    }

    // Fetch all movies once sections are loaded
    if (sectionsResult is NetworkResult.Success) {
        val sections = (sectionsResult as NetworkResult.Success).data.orEmpty()
        Log.d("SectionListScreen", "Sections fetched successfully: ${sections.size} sections")
        LaunchedEffect(sections) {
            Log.d("SectionListScreen", "Fetching movies for all sections...")
            viewModel.fetchAllMovies(sections) // Fetch movies for all sections
        }
    }

    when {
        sectionsResult is NetworkResult.Loading || moviesByTagsResult is NetworkResult.Loading -> {
            Log.d("SectionListScreen", "Loading state detected: Displaying global loading indicator")
            //CircularProgressIndicator(modifier = Modifier.fillMaxSize()) // Global loading indicator
        }
        sectionsResult is NetworkResult.Error -> {
            val errorMessage = (sectionsResult as NetworkResult.Error).message
            Log.e("SectionListScreen", "Error loading sections: $errorMessage")
            Text(
                text = "Error loading sections: ${(sectionsResult as NetworkResult.Error).message}",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
        moviesByTagsResult is NetworkResult.Error -> {
            Text(
                text = "Error loading movies: ${(moviesByTagsResult as NetworkResult.Error).message}",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
        sectionsResult is NetworkResult.Success && moviesByTagsResult is NetworkResult.Success -> {
            val moviesBySection = (moviesByTagsResult as NetworkResult.Success).data.orEmpty()
            Log.d("SectionListScreen", "Movies fetched successfully for all sections")

            LazyColumn {

                itemsIndexed (moviesBySection.entries.toList()) { index,entry ->
                    val sectionTitle = entry.key // The section title (key of the map)
                    val movies = entry.value.orEmpty().filterNotNull()
                    Log.d("SectionListScreen", "Displaying section: $sectionTitle with ${movies.size} movies")


                    NobinoSpecialRowBySection2(sectionTitle,navController,movies[index],"COURSE")


                    SectionItemWithMovies(
                        sectionTitle = sectionTitle,
                        movies = movies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}


@Composable
fun SectionItemWithMovies(
    sectionTitle: String,
    movies: List<MovieResult.DataMovie.Item?>?,
    onMovieClick: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Section Title
        Text(text = sectionTitle, style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.height(8.dp))

        // Movies LazyRow
        LazyRow {
            items(movies.orEmpty().filterNotNull()) { movie ->
                // MovieItem(movie = movie, onClick = { onMovieClick(movie.id ?: 0) })
                SliderItemByTags(movie)
            }



        }
    }
}





















