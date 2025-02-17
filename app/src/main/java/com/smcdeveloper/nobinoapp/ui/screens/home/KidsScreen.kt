package com.smcdeveloper.nobinoapp.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.ui.screens.product.KidsMovieScreen
import com.smcdeveloper.nobinoapp.util.Constants
import com.smcdeveloper.nobinoapp.util.LocalelUtils
import com.smcdeveloper.nobinoapp.viewmodel.KidsViewModel

@Composable
fun KidsScreen(navController: NavHostController)

{
    Kids(navController=navController)

}

@Composable
fun Kids(
    navController: NavHostController,
    viewModel: KidsViewModel = hiltViewModel()
)


{
    LocalelUtils.setLocale(LocalContext.current, Constants.USER_LANGUAGE)

    /*LaunchedEffect(Unit ){

        refreshDataFromServer(viewModel)
    }*/


    val preferredOrder = listOf(12,13,14,15,16,17,18,19)


  // GetSlider()



   // GetSlider()
    KidsMovieScreen( viewModel,navController,preferredOrder)











           /*   Column (Modifier.padding(20.dp)
                  .fillMaxSize()


              )
              {


                 // item { getSlider()}

                   // ImageSlider()
               //   getSlider()
                 // MovieScreen1( viewModel,preferredOrder,navController)





          }
*/




































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









