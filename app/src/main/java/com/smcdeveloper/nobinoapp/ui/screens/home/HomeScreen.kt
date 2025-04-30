package com.smcdeveloper.nobinoapp.ui.screens.home

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.ui.screens.product.MovieScreenHome
import com.smcdeveloper.nobinoapp.util.AppConfigManager
import com.smcdeveloper.nobinoapp.util.ConnectivityObserver
import com.smcdeveloper.nobinoapp.util.Constants
import com.smcdeveloper.nobinoapp.util.LocalelUtils
import com.smcdeveloper.nobinoapp.util.NetworkConnectivityObserver

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
      val apiError by viewModel.apiError.collectAsState()
      lateinit var connectivityObserver: ConnectivityObserver







    LaunchedEffect(Unit) {





    }






    LocalelUtils.setLocale(LocalContext.current, Constants.USER_LANGUAGE)
    val token by AppConfigManager.userToken.collectAsState()

    if (token != null) {
        // Your app now uses the updated token/config
        Log.d("token","Token is updated: $token")
    } else {
        Log.d("token", "No token yet")
    }




    var showExitDialog by remember { mutableStateOf(false) }
    var showInterDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    connectivityObserver = NetworkConnectivityObserver(context)


    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )

       when(status)
       {
           ConnectivityObserver.Status.Available->{
             //  showInterDialog=false

           }

           else->
           {
//               showInterDialog=true



           }







       }









    // Intercept the back press
    BackHandler {
        showExitDialog = true
    }


    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit App") },
            text = { Text("Are you sure you want to exit?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        // Exit the app by finishing the activity
                        (context as? Activity)?.finish()
                    }
                ) {
                    Text("Exit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if(showInterDialog)
    {
        ShowDialog("INTERNET","No Internet Connection",context,)
        {
            showInterDialog=false




        }



    }


















    /*LaunchedEffect(Unit ){

        refreshDataFromServer(viewModel)
    }*/

   val preferredOrder = listOf(1,2,3,4,5,6,7,8,9,10)
  //  val preferredOrder = listOf(12,13,14,15,16,17,18,19)


  // GetSlider()



   // GetSlider()
    MovieScreenHome( viewModel,navController,preferredOrder)













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


@Composable
fun ShowDialog(title:String,dialogText:String,context: Context,onDismiss:()->Unit)
{

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(title) },
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(
                onClick = {
                   onDismiss()
                    // Exit the app by finishing the activity
                    (context as? Activity)?.finish()
                }
            ) {
                Text("Exit")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss () }) {
                Text("Cancel")
            }
        }
    )










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









