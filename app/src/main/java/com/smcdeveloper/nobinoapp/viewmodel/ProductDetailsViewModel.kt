package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProductDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val repository: ProductDetailsRepository):ViewModel() {

    private val _product = MutableStateFlow<NetworkResult<ProductModel>>(NetworkResult.Loading())
    val product: StateFlow<NetworkResult<ProductModel>> get() = _product.asStateFlow()




    fun getProductDetails(productId: Int) {

        viewModelScope.launch {
            Log.d("productdatils","product id is:...${productId}")
            val results = repository.getProductDetails(productId)
           _product.value = results
            Log.d("productdatils","99999999999"+results.data?.data?.name.toString())

        }






    }





 }
















































