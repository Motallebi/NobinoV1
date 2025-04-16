package com.smcdeveloper.nobinoapp.ui.screens.favorit


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.smcdeveloper.nobinoapp.data.model.favorit.Favorite
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.HtmlText
import com.smcdeveloper.nobinoapp.ui.component.NobinoMainTitleHeader



import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.FavoritesViewModel

@Composable
fun FavoriteScreen(navController: NavHostController,
viewModel: FavoritesViewModel= hiltViewModel()
)
{

    FavoriteListScreen(viewModel,navController)



}

@Composable
fun ShowData()
{












}






@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteListScreen(viewModel: FavoritesViewModel,navController: NavHostController) {
    //  val movies by viewModel.movies.collectAsState()

    val movies =
        viewModel.getUserFavorites(pageSize = 20, pageNumber = 1).collectAsLazyPagingItems()



  //  val isRefreshing = movies.loadState.refresh is LoadState.Loading
/*

    Log.d("refreshing", movies.loadState.toString() )

    val swipeRefreshState = rememberPullRefreshState(isRefreshing, onRefresh =

    {
        movies.refresh()

    }





    )
*/































    Surface(
        modifier = Modifier.fillMaxSize()
            //.background(Color.Red)
            .padding(top = 50.dp)




    )


    {
















       NobinoMainTitleHeader("favorits",navController)

///


        Box(
            modifier = Modifier
                .fillMaxSize()
                 .padding(top = 20.dp)
                .padding(16.dp)
                .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(8.dp)) // ✅ Light border
                .background(Color.Black) // ✅ Dark background
                .padding(16.dp) // ✅ Inner padding
              // .pullRefresh(swipeRefreshState)

        )


        {

         /*   PullRefreshIndicator(
                refreshing = isRefreshing,
                state = swipeRefreshState,
                modifier = Modifier.align(Alignment.Center)
            )
*/





            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp) // ✅ Space between items
            )



            {
                items(movies.itemCount) { index -> // ✅ Show 3 items at a time

                    MovieItem(movies[index]!!,navController,index.toString())


                }


                if (movies.loadState.append is LoadState.Loading ) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }

                }
            }
















        }





    }








}

@Composable
fun MovieItem(movie: Favorite.FavoriteData.Item,navController: NavHostController,num:String) {

    Card(

    )

    {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray, RoundedCornerShape(4.dp)) // ✅ Card-like look
                .padding(4.dp)
        )

        {

            AsyncImage(
                model = IMAGE_BASE_URL + movie.images?.get(0)?.src,
                contentDescription = "Movie Thumbnail",
                modifier = Modifier
                    .width(80.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(num)


            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.name.toString(),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    softWrap = true




                )
            }



            Button(
                onClick = {
                /* Play movie */

                    if(movie.category.toString() != "SERIES") {

                        navController.navigate(
                            Screen.ProductDetails.withArgs(
                                movie.id.toString()


                            )
                        )
                    }
                    else if (movie.category.toString() == "SERIES")
                    {
                        navController.navigate(Screen.SeriesDetailScreen.withArgs(
                            movie.id.toString())
                        )


                    }







                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = MaterialTheme.shapes.medium
                //modifier = Modifier.align(Alignment.Start)
            )
            {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "پخش فیلم", color = Color.White)
            }



        }

        HtmlText(movie.shortDescription.toString(), textColor = Color.White)




    }











       // Spacer(modifier = Modifier.width(12.dp))


          //  Text(text = "قسمت ${movie.episode}", color = Color.Gray, fontSize = 14.sp)

          //  Spacer(modifier = Modifier.height(4.dp))



         //   Spacer(modifier = Modifier.height(8.dp))


        }







