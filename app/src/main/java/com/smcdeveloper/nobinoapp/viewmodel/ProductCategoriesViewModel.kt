package com.smcdeveloper.nobinoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductCategories
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProductCategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductCategoriesViewModel @Inject constructor(
    private val repository: ProductCategoriesRepository


):ViewModel()  {


    private val _categories = MutableStateFlow<NetworkResult<List<ProductCategories.ProductCategoryData>>>(NetworkResult.Loading())
    val categories: StateFlow<NetworkResult<List<ProductCategories.ProductCategoryData>>> get() = _categories.asStateFlow()


  //  private val _categories = MutableStateFlow<NetworkResult<List<ProductCategories.ProductCategoryData>>>(NetworkResult.Loading())
   // val categories: StateFlow<NetworkResult<List<ProductCategories.ProductCategoryData>>> get() = _categories.asStateFlow()

   // private val _productcategories = MutableStateFlow<NetworkResult<ProductCategories>>(NetworkResult.Loading())
   // val productCategories: StateFlow<NetworkResult<ProductCategories>> = _productcategories




    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

         init {
             getProductCategories()
         }





            fun getProductCategories()
            {
                viewModelScope.launch {
                    try {
                        _isLoading.value = true
                        _categories.value = repository.getProductCategories()
                    } catch (e: Exception) {
                        _categories.value = NetworkResult.Error("Failed to load categories")
                    } finally {
                        _isLoading.value = false
                    }
                }
            }




            }









