package com.smcdeveloper.nobinoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.bb.MovieInfoData
import com.smcdeveloper.nobinoapp.data.model.bb.MovieResult
import com.smcdeveloper.nobinoapp.data.model.nn.Item
import com.smcdeveloper.nobinoapp.data.model.nn.MoviesData
import com.smcdeveloper.nobinoapp.data.model.product.product
import com.smcdeveloper.nobinoapp.data.model.testrest.PostResponseModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository):ViewModel() {

    val products = MutableStateFlow<NetworkResult>(NetworkResult.Loading())

             fun getProduct() {

               viewModelScope.launch {
                   products.emit(repository.getProducts())


               }





           }



}