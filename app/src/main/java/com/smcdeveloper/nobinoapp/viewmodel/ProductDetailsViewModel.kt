package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.datastore.DataStoreRepository
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductCategories
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProductDetailsRepository
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel.Companion.USER_TOKEN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductDetailsRepository,
    private val dataStoreRepository:DataStoreRepository


):ViewModel()

{







    private val _product = MutableStateFlow<NetworkResult<ProductModel>>(NetworkResult.Loading())
    val product: StateFlow<NetworkResult<ProductModel>> get() = _product.asStateFlow()

    private val _relatedMovies = MutableStateFlow<NetworkResult<MovieResult>>(NetworkResult.Loading())
    val relatedMovies: StateFlow<NetworkResult<MovieResult>> get() = _relatedMovies.asStateFlow()





    fun getProductDetails(productId: Int) {

        viewModelScope.launch {
            Log.d("productdatils","product id is:...${productId}")
            val token = dataStoreRepository.getString(USER_TOKEN_KEY).toString()
            val results = repository.getProductDetails(productId,"Bearer "+token)
            Log.d("productdatils","product id is:...${productId}")
            Log.d("Token","product id is:...${token}")

           _product.value = results
            Log.d("productdatils","99999999999"+results.data?.data?.name.toString())
            Log.d("productdatils","99999999999----videoLink"+results.data?.data?.videoLink.toString())

        }






    }


    fun getRelatedMovies(productId: Int,tags:String) {
        viewModelScope.launch {
            _relatedMovies.value = NetworkResult.Loading()
            try {
                val response = repository.fetchRelatedMovies(productId,tags) // Fetch data from API
                _relatedMovies.value = NetworkResult.Success(response.data)
            } catch (e: Exception) {
                _relatedMovies.value = NetworkResult.Error(e.localizedMessage ?: "Failed to load related movies.")
            }
        }
    }
















}
















































