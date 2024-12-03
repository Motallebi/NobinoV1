package com.smcdeveloper.nobinoapp.ui.screens.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.model.bb.MovieResult
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.ui.component.FilmBox
import com.smcdeveloper.nobinoapp.util.Constants.LOG_TAG
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun getAllMovies(viewModel: HomeViewModel = hiltViewModel())
{

    var movieList by remember {

        mutableStateOf<List<MovieResult.DataMovie.Item?>>(emptyList())


    }



    val results by viewModel.products.collectAsState()
       when(results)
       {
           is NetworkResult.Success -> {

               results.data?.movieInfo?.items.let { data->
                   if (data != null) {
                       movieList= data
                   }



               }




           }
           is NetworkResult.Error -> {

           }
           is NetworkResult.Loading -> {

           }



       }




    Log.d("nobino","internet")


    LaunchedEffect(true) {

        viewModel.products.collectLatest { result ->
            when (result) {
                is NetworkResult.Success -> {

                    result.data?.movieInfo.let {

                      //  movieList=it




                    }





                    result.data?.movieInfo?.items?.forEach {

                        Log.d(LOG_TAG,"Movie Name is${it?.name.toString()}")
                        Log.d(LOG_TAG,"Movie Images.....${it?.images?.forEach {

                            Log.d(LOG_TAG,"Movie Name is${it?.imageType.toString()}")
                            Log.d(LOG_TAG,"Movie Name is${it?.src.toString()}")
                            
                            
                        }}")





                    }









                    //MovieResult

                    Log.d(LOG_TAG, "success")
                    Log.d(LOG_TAG, result.data.toString())


                    Log.d(LOG_TAG,"---------"+result.message.toString())
                    Log.d(LOG_TAG,"---88------"+result.message.toString())
                    Log.d(LOG_TAG,"---8877------"+result.message.toString())
                    Log.d(LOG_TAG,"---887888------"+result.message.toString())


                    Log.d(LOG_TAG,"success result"+result.data.toString())

                    Log.d(LOG_TAG,result.data.toString())










                }

                is NetworkResult.Error -> {
                    Log.d(LOG_TAG, "Error")
                    Log.d(LOG_TAG, result.message.toString())



                }

                is NetworkResult.Loading -> {
                    Log.d(LOG_TAG, "loading")

                }


            }


        }


    }
    ShowMiveBox(movieList)




}

@Composable
fun ShowMiveBox(data:List<MovieResult.DataMovie.Item?>)
{
    Box(
        modifier = Modifier.fillMaxSize(0.5f)
            .background(Color.Green),
        contentAlignment = Alignment.TopStart


    )
    {
        LazyColumn() {

            itemsIndexed(data)
            {
                  index,item->


              // Text("${index}---${item?.name.toString()}")
                FilmBox("${index}---${item?.name.toString()}")






         //    Spacer(modifier = Modifier.fillMaxWidth())





            }







        }




    }



}