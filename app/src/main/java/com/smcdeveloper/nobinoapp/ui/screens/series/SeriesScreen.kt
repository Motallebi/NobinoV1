package com.smcdeveloper.nobinoapp.ui.screens.series

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
import com.smcdeveloper.nobinoapp.viewmodel.SeriesViewModel

@Composable
fun SeriesScreen1(navController: NavHostController)

{
    Series1(navController=navController)

}

@Composable
fun Series1(
    navController: NavHostController,
    viewModel: SeriesViewModel = hiltViewModel()
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





        // getAllMovies()
           getSeriesSlider()


    }

}

private suspend fun refreshDataFromServer(viewModel: SeriesViewModel) {
  // viewModel.getProduct()
   // viewModel.getProductBySize()
   // viewModel.getSlider()
    viewModel.getSeriestBySize()


   //viewModel.productsListBySize()
}




  //  NobinoButton("دریافت کد از طریق پیامک")
    // Text("hi.......")
   // NobinoOutlineButton()









