package com.smcdeveloper.nobinoapp.ui.screens.product

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.theme.backgroundDark
import com.smcdeveloper.nobinoapp.ui.theme.nobinoSmall
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@Composable
fun ProductScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    tag:String,
    categoryName:String






)






{
    Product(navController,viewModel,tag,categoryName)



































}

@Composable
fun Product(navController: NavHostController,
            viewModel: HomeViewModel,tag: String,categoryName: String



) {

    // Log.d(LOG_TAG,"the tag is ${tag}" )
    LaunchedEffect(true) {


        //  viewModel.getProductsBySpecialTag(tag)
        viewModel.setCategoryId(tag)
        Log.d("NobinoApp","ProductScreenIslunched..")






    }

    val products = viewModel.getMoviesByCategory(tag,categoryName).collectAsLazyPagingItems()


  //  val movies = viewModel.getMoviesByCategory(tag).collectAsLazyPagingItems()
   // val lazyPagingItems  = viewModel.moviesFlow.collectAsLazyPagingItems()
   // val items= lazyPagingItems<MovieResult.DataMovie.Item>()


    Text(tag,
        style = MaterialTheme.typography.nobinoSmall,
        modifier = Modifier.padding(top = 5.dp)
            .background(Color.Red)



    )

    Box(modifier = Modifier.fillMaxSize()
       // .wrapContentSize(unbounded = true)
       // .background(Color.Red)
        .padding(top = 60.dp)
        .height(700.dp)
        .background(MaterialTheme.colorScheme.secondary)
        ,

        contentAlignment = Alignment.TopStart


    )
    {
        Text("Product Screen..........")



        /*Column {
            Text("hiiiii")
            Text("-----${tag}--------")
         //  Text(movies.get(0)?.name.toString())

        }*/


        Column {
            /*LazyColumn {
                items(movies.itemCount) { index ->
                    Log.d("NobinoApp", "Rendering movie: ${movies.get(index)?.name}")
                    movies.get(index)?.let {
                        Text(
                            text = (index.toString() + it.name) ?: "No Name"


                        )
                    }
                }


            }*/

        }



        DynamicMoviesGrid(
            products = products,

            onMovieClick = { movie->
                Log.d("category", "Clicked movie: ${movie.name}")

                val movieId= movie.id
                Log.d("category", "Clicked movie: $movieId")


                    navController.navigate(Screen.ProductDetails.withArgs("$movieId"))







            }




        )





     /*   LazyColumn {
            items(
                count = movies.itemCount,
                key = movies.itemKey{movie-> movie.id.toString() },
                contentType = movies.itemContentType{"movie"}
            )

            {
                    index->

                Text(movies[index]!!.name.toString())

                //MovieCardtestByTag(movies[index]!!)
                MoviesGrid(movies)



            }




            }*/


        }





    }








@Composable
fun ShowLazyGridView()
{





}


@Composable
fun MoviesGrid(


    product: LazyPagingItems<MovieResult.DataMovie.Item>, // Passing in the LazyPagingItems<Movie>
    modifier: Modifier = Modifier,
    onMovieClick: () -> Unit = {} // Lambda for handling item clicks
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 3 columns for the grid
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(product.itemCount) { index ->
            val movie = product[index]
            if (movie != null) {
                MovieItem(
                    movie = movie,
                    onClick = { onMovieClick() }
                )
            } else {
                // Placeholder for loading or null state
                Box(
                    modifier = Modifier
                        .aspectRatio(1f) // Ensures the item is square
                        .background(Color.Gray)
                )
            }
        }
    }
}

@Composable
fun MovieItem(movie: MovieResult.DataMovie.Item, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // Ensures the item fits into a square
            .padding(4.dp)
            .background(Color.LightGray)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = movie.name.toString(), style = MaterialTheme.typography.nobinoSmall)

    }
}

@Composable
fun DynamicMoviesGrid(
    products: LazyPagingItems<MovieResult.DataMovie.Item>, // A list of mixed data types (movies and informational items)
    modifier: Modifier = Modifier,
    onMovieClick: (MovieResult.DataMovie.Item) -> Unit = {}, // Handles movie card clicks
    onInfoClick: (MovieResult.DataMovie.Item) -> Unit = {} // Handles informational card clicks
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Adjust to your desired grid layout
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    )
    {
        items(products.itemCount)
        {
            index ->
            when (val item = products[index]?.category) {
                 "MOVIES"-> {
                products[index]?.let {
                    MovieCard(
                        movie = it,
                        onClick = { products[index]?.let { movie -> onMovieClick(movie) }

                             Log.d("category clicked..",products.get(index)?.name.toString())



                        }
                    )
                    Log.d("category","Movies")

                }
                }
                "SERIES" -> {
                products[index]?.let {
                    InfoCard(
                        info = it,
                        onClick = { products[index]?.let { movie -> onMovieClick(movie) } })
                    Log.d("category","Movies")


                }



                }
                else->
                {

                    products[index]?.let {
                        MovieCard(
                            movie = it,
                            onClick = { products[index]?.let { movie -> onMovieClick(movie) } }
                        )
                        Log.d("category","others")

                    }







                }




            }
        }
    }
}






@Composable
fun MovieCard(movie: MovieResult.DataMovie.Item, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
            .clickable { onClick() }
            .fillMaxWidth()
    ) {
        AsyncImage(
            model =  "https://vod.nobino.ir/vod/"+movie.images?.get(0)?.src.toString(), // Use Coil or similar libraries for image loading
            contentDescription = movie.shortDescription,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Ensures square aspect ratio for images
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        movie.name?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.nobinoSmall,
                       // color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

    }
}

@Composable
fun InfoCard(info: MovieResult.DataMovie.Item, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundDark)
            .clickable { onClick() }
            .fillMaxWidth()
    ) {
        AsyncImage(
            model =  "https://vod.nobino.ir/vod/"+info.images?.get(0)?.src.toString(),
            contentDescription = info.shortDescription,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        info.name?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.nobinoSmall,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        info.translatedName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.nobinoSmall,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }



    }
}
















    /*  LazyColumn {
        items(
            count = productList.itemCount,
            key = productList.itemKey() { MovieResult -> MovieResult.movieInfo.toString() },
            contentType = productList.itemContentType { "product" }
        )
        { index ->

            Text(productList[index]!!.movieInfo!!.items?.get(index)!!.name.toString())

        }
    }
    }*/


    /*
    Column {
        Text("Product Screen..........")

        // SearchBarSection()
        LazyColumn(Modifier.fillMaxSize()){
            //paging3
            items(
                count = productList.itemCount,





                key = productList.itemKey(){MovieResult -> MovieResult.id.toString()},
                contentType = productList.itemContentType{"product"}
            ){index ->

                Text(productList[index]?.name.toString())

            }
        }
    }*/























