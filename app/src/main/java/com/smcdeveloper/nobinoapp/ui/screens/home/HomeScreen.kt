package com.smcdeveloper.nobinoapp.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import kotlinx.coroutines.flow.collectLatest

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
    LaunchedEffect(true) {


        refreshDataFromServer(viewModel)
    }






 Column(
    modifier = Modifier.padding(top = 30.dp)


 )
 {
     Button(onClick = {

         Log.d("nobino","Click on ME!!!")















     }
     , modifier = Modifier.fillMaxWidth()



     )

     {
         Text("Click me")
         getAllMovies()





         }




     }






     }


private suspend fun refreshDataFromServer(viewModel: HomeViewModel) {
    viewModel.getProduct()
}




  //  NobinoButton("دریافت کد از طریق پیامک")
    // Text("hi.......")
   // NobinoOutlineButton()









