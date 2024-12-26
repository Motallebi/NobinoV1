package com.smcdeveloper.nobinoapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.ui.component.NobinoGradientCard
import com.smcdeveloper.nobinoapp.util.Constants
import com.smcdeveloper.nobinoapp.util.LocalelUtils
import kotlinx.coroutines.delay

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

    LaunchedEffect(true) {

        refreshDataFromServer(viewModel)
    }






    Column(
     //   modifier = Modifier.padding(top = 30.dp)


    )
    {

        Text("NOBINO--------NONINO")
        NobinoGradientCard("Nobino")





       //  getAllMovies()
        //   getSlider()

        ImageSlider()
        //--------------
     //   GetSliderBy

        GetSliderByTag5()
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


    val preferredOrder = listOf(3, 1, 5, 2, 4, 6, 8, 7, 10, 9) // Your custom order
    preferredOrder.forEach { tagId ->
        viewModel.fetchMoviesForTagGroup1(tagId)
    }





   //viewModel.getProduct()
  // viewModel.getProductBySize()
  viewModel.getSlider()
   //viewModel.fetchMoviesByTag(1)
   //delay(1000)
  // viewModel.fetchMoviesByTag(2)

  //  viewModel.getMoviesByTags()




   //viewModel.productsListBySize()
}




  //  NobinoButton("دریافت کد از طریق پیامک")
    // Text("hi.......")
   // NobinoOutlineButton()









