package com.smcdeveloper.nobinoapp.ui.screens.demo

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@SuppressLint("RememberReturnType")
@Composable
fun MovieScreen1(viewModel: HomeViewModel= hiltViewModel()) {
    val movies =


        viewModel.getMoviesByCategory3(
            tag = "",
            categoryName = "MOVIE",
            countries = "",
            name = "A",
            size = 20
        ).collectAsLazyPagingItems()


    val loadState = movies.loadState
    val isInitialLoading =
        loadState.refresh is LoadState.Loading && movies.itemCount == 0 // ✅ Show for first load
    val isNextPageLoading = loadState.append is LoadState.Loading // ✅ Show when loading more data
    val isError = loadState.refresh is LoadState.Error
    // val loadState = movies.loadState.refresh


    // val isLoading = loadState is LoadState.Loading
    //  val isError = loadState is LoadState.Error

    var show = true

    if (show) {

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isInitialLoading -> {
                    Log.d("UI_LOG", "Showing loading indicator...")
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) // 🔄 Show Loader
                }

                isError -> {
                    val errorMessage = (loadState as LoadState.Error).error.localizedMessage
                    Log.e("UI_LOG", "Error fetching movies: $errorMessage")
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn {
                        items(movies.itemCount) { index ->
                            movies[index]?.let { movie ->
                                Text(
                                    text = movie.name.toString(),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        item {
                            if (isNextPageLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(16.dp)
                                        //.align(Alignment.CenterHorizontally)
                                )
                            }


                        }




                        Log.d("UI_LOG", "Displaying movie list...")
                    }
                }
            }
        }

    }

}

    /*LazyColumn {
        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                Text(text ="--"+index+"---" +movie.name.toString(), modifier = Modifier.padding(8.dp))
            }
        }
    }
    Log.d("UI_LOG", "Displaying movie list...")*/

