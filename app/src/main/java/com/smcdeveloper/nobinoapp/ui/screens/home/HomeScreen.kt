package com.smcdeveloper.nobinoapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.ui.screens.product.MovieScreen1
import com.smcdeveloper.nobinoapp.util.Constants
import com.smcdeveloper.nobinoapp.util.LocalelUtils

@Composable
fun HomeScreen(navController: NavHostController)

{
    Home(navController=navController)

}

@Composable
fun Home(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
)


{
    LocalelUtils.setLocale(LocalContext.current, Constants.USER_LANGUAGE)

    LaunchedEffect(Unit ){

        refreshDataFromServer(viewModel)
    }

    val preferredOrder = listOf(1,2,3,4,5,6,7,8,9,10)

      Scaffold(

          topBar ={ NobinoTop(navController)}



      ) { PaddingValues->

              Column(Modifier.padding(PaddingValues)) {

                  MovieScreen1( viewModel,preferredOrder,navController)


              }







      }














    Column(
     //   modifier = Modifier.padding(top = 30.dp)


    )
    {

      //  Text("NOBINO--------NONINO")
        //NobinoGradientCard("Nobino")




       //  getAllMovies()
        //   getSlider()

      //  ImageSlider()
        //--------------
     //   GetSliderBy

     //   GetSliderByTag5(navController = navController)
      //  GetSlider6(navController = navController)
       // MovieTagProcessor(navController = navController)

       // GetSliderByTag2()
    //   GetSliderByTag2()









    }

}

private suspend fun refreshDataFromServer(viewModel: HomeViewModel) {

   //viewModel.fetchMoviesForTags()
   // viewModel.fetchMoviesForParameters()
   /* (1..10).forEach { tagId ->
        viewModel.fetchMoviesForTagGroup(tagId)
    }*/


    val tags = listOf("[action]", "[comedy]", "[drama]", "[thriller]") // Replace with your actual tags






    val preferredOrder = listOf(1,2,3,4,5,6,7,8,9,10) // Your custom order
   /* preferredOrder.forEach { tagId ->
        viewModel.fetchMoviesForTagGroup1(tagId)
    }*/
    //viewModel.fetchMoviesByTags(preferredOrder)
    //viewModel.fetchMoviesForTags(tags)
   // viewModel.fetchMoviesForTagIds(preferredOrder)









   //viewModel.getProduct()
  // viewModel.getProductBySize()
  //viewModel.getSlider()
   //viewModel.fetchMoviesByTag(1)
   //delay(1000)
  // viewModel.fetchMoviesByTag(2)

  //  viewModel.getMoviesByTags()




   //viewModel.productsListBySize()
}




  //  NobinoButton("دریافت کد از طریق پیامک")
    // Text("hi.......")
   // NobinoOutlineButton()









