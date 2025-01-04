package com.smcdeveloper.nobinoapp.ui.screens.Product

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LOG_TAG
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoSmall
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@Composable
fun ProductScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    tag:String






)






{
    Product(navController,viewModel,tag)



































}

@Composable
fun Product(navController: NavHostController,
            viewModel: HomeViewModel,tag: String



) {

    // Log.d(LOG_TAG,"the tag is ${tag}" )
    LaunchedEffect(true) {


        //  viewModel.getProductsBySpecialTag(tag)
        viewModel.setCategoryId(tag)
        Log.d("NobinoApp","ProductScreenIslunched..")






    }

    val movies = viewModel.getMoviesByCategory(tag).collectAsLazyPagingItems()

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



        LazyColumn {
            items(
                count = movies.itemCount,
                key = movies.itemKey{movie-> movie.id.toString() },
                contentType = movies.itemContentType{"movie"}
            )
            {
                    index-> Text(movies[index]!!.name.toString())

            }


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























