package com.smcdeveloper.nobinoapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey

@Composable
fun ShowAllProduct(
    viewModel: HomeViewModel= hiltViewModel(),
    navController: NavHostController,
    tag:String





)
{

    LaunchedEffect(true)
    {
       // viewModel.getProductsBySpecialTag(tag)


    }


    val productList =  viewModel.productByCategoryList.collectAsLazyPagingItems()

    Column {
       // SearchBarSection()
        LazyColumn(Modifier.fillMaxSize()){
            //paging3
            items(
                count = productList.itemCount,





               // key = productList.itemKey(){MovieResult -> MovieResult.id.toString()},
                contentType = productList.itemContentType{"product"}
            ){index ->

            //     Text(productList[index]?.name.toString())

            }
        }
    }








}

