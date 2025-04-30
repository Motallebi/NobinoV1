package com.smcdeveloper.nobinoapp.ui.screens.product

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.navigation.BottomNavigationBar
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.LayeredImageBackgroundCard
import com.smcdeveloper.nobinoapp.ui.component.SeriesCardWithAnimation
import com.smcdeveloper.nobinoapp.ui.screens.home.NobinoTop
import com.smcdeveloper.nobinoapp.ui.theme.backgroundDark
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoSmall
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@Composable
fun ProductScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    tag:String,
    categoryName:String,
    categoryTitle:String






)






{



    Product(navController,viewModel,tag,categoryName,categoryTitle)
    //ShowContent1()



































}

@Composable
fun Product(navController: NavHostController,
            viewModel: HomeViewModel,tag: String,categoryName: String,
            categoryTitle: String




)

{

    // Log.d(LOG_TAG,"the tag is ${tag}" )
    LaunchedEffect(true) {


        //  viewModel.getProductsBySpecialTag(tag)
        viewModel.setCategoryId(tag)








    }

    val products = viewModel.getMoviesByCategory(tag=tag,categoryName=categoryName, countries = "", name = "", size = 20).collectAsLazyPagingItems()





    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween



    )

    {
        Text(categoryTitle,
            style = MaterialTheme.typography.nobinoLarge,
            modifier = Modifier.padding(top = 5.dp)
               // .background(Color.Red)



        )

        Icon(painterResource(R.drawable.arrow_left),"",
            tint = Color.White,
            modifier = Modifier.clickable {

                navController.navigateUp()





            }





        )








    }






    Text(categoryName)


    Box(modifier = Modifier.fillMaxSize()
       // .wrapContentSize(unbounded = true)
       // .background(Color.Red)
        .padding(top = 60.dp)
        .height(700.dp)
        .background(MaterialTheme.colorScheme.primary)
        ,

        contentAlignment = Alignment.TopStart


    )
    {
      //  Text("Product Screen..........")



        /*Column {
            Text("hiiiii")
            Text("-----${tag}--------")
         //  Text(movies.get(0)?.name.toString())

        }*/






        DynamicMoviesGrid(
            products = products,

            onMovieClick = { movie->
                Log.d("category", "Clicked movie: ${movie.name}")

                val movieId= movie.id
                Log.d("category", "Clicked movie: $movieId")

                     if(movie.category.toString() == "SERIES" )
                     {
                         Log.d("Catis","cat is .... ${movie.category.toString()}")




                         navController.navigate(Screen.SeriesDetailScreen.withArgs("$movieId"))
                     }
                     else if(movie.category.toString() == "MOVIE")
                     {
                         Log.d("Catis","cat is .... MOVIE Or Other")
                         Log.d("Catis","cat is .... ${movie.category.toString()}")
                         Log.d("Catis","cat is .... MovieId$movieId")
                         navController.navigate(Screen.ProductDetails.withArgs("$movieId"))


                     }










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
fun ShowContent1()
{









    Box(modifier = Modifier.fillMaxSize(0.5f)
        // .wrapContentSize(unbounded = true)
        // .background(Color.Red)
        .padding(top = 60.dp)
        .height(700.dp)
        .background(MaterialTheme.colorScheme.primary),


        contentAlignment = Alignment.TopStart


    )
    {
        Text("Test Screen")


    }




}



@Composable
fun ShowContent(navController: NavHostController) {

    Scaffold(

        topBar = { NobinoTop(navController) },
        bottomBar = {
            BottomNavigationBar(navController, onItemClick = {
                navController.navigate(it.route)

            }

            )


        },









        ) {

            paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                // .wrapContentSize(unbounded = true)
                // .background(Color.Red)
                .padding(top = 60.dp)
                .height(700.dp)
                .background(MaterialTheme.colorScheme.primary),


            contentAlignment = Alignment.TopStart


        )
        {
            Text("Test Screen")


        }




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
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
        ,
        contentPadding = PaddingValues(16.dp),

        verticalArrangement = Arrangement.spacedBy(32.dp),
       horizontalArrangement = Arrangement.spacedBy(8.dp)


    )
    {
        items(
          count  =  products.itemCount,
          key = products.itemKey()

        )
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
                    SeriesCard(
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
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(200.dp)
           // .padding(8.dp)
           .clip(RoundedCornerShape(15.dp))
           // .background(Color.Red, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }

            //.fillMaxWidth()
    )
    {
        AsyncImage(


            model =  "https://vod.nobino.ir/vod/"+movie.images?.get(0)?.src.toString(), // Use Coil or similar libraries for image loading
            contentDescription = movie.shortDescription,
            modifier = Modifier
                .fillMaxSize()
               // .aspectRatio(1f) // Ensures square aspect ratio for images
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.FillBounds
        )
    //    Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(0.7f)),
                        startY = 0f
                    )
                )


            //.background(Color.Red)
            ,
            contentAlignment = Alignment.BottomStart


        )
        {


        }



        Box(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 5.dp)
                //.background(Color.Red)
            ,
            contentAlignment = Alignment.BottomStart,




        )








        {

            movie.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.nobinoSmall,
                    // color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    maxLines = 1

                )
            }



        }









    }






}

@Composable
fun InfoCard(info: MovieResult.DataMovie.Item, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(200.dp)
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
        Text("SERIES",

            style = MaterialTheme.typography.bodyMedium

            )



    }
}

@Composable
fun SeriesCard(info: MovieResult.DataMovie.Item,onClick: () -> Unit)
{

 //   LayeredImageBackgroundCard(info)
    SeriesCardWithAnimation(info)
    {
        onClick()



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























