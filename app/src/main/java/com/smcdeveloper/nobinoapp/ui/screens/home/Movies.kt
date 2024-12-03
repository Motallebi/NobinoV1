package com.smcdeveloper.nobinoapp.ui.screens.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.model.bb.MovieResult
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.util.Constants.LOG_TAG
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun getAllMovies(viewModel: HomeViewModel = hiltViewModel())
{
    Log.d("nobino","internet")


    LaunchedEffect(true) {

        viewModel.products.collectLatest { result ->
            when (result) {
                is NetworkResult.Success -> {





                    //MovieResult

                    Log.d(LOG_TAG, "success")
                    Log.d(LOG_TAG, result.data.toString())


                    Log.d(LOG_TAG,"---------"+result.message.toString())
                    Log.d(LOG_TAG,"---88------"+result.message.toString())
                    Log.d(LOG_TAG,"---8877------"+result.message.toString())

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
}