package com.smcdeveloper.nobinoapp.data.repository

import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductCategories
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.ProductCategoriesApiInterface
import com.smcdeveloper.nobinoapp.data.remote.ProductDetailsApiInterface
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import javax.inject.Inject

class ProductCategoriesRepository @Inject constructor(
    private val api: ProductCategoriesApiInterface,




): BaseApiResponse2() {


   /* suspend fun getProductCategories():NetworkResult<List<ProductCategories.ProductCategoryData>> =

        safeApiCall {

            api.getProductCategories()


        }*/




    suspend fun getProductCategories(): NetworkResult<List<ProductCategories.ProductCategoryData>> {
        return safeApiCall {
            api.getCategoriesResponse() // Assume this returns Response<ApiResponse<List<ProductCategoryData>>>
        }.let { result ->
            when (result) {
                is NetworkResult.Success -> {
                    // Map the response's `data` to the desired result
                    val categories = result.data?.data ?: emptyList()
                    NetworkResult.Success(categories)
                }
                is NetworkResult.Error -> {
                    NetworkResult.Error(result.message ?: "An unknown error occurred.")
                }
                is NetworkResult.Loading -> {
                    NetworkResult.Loading()
                }
            }
        }
    }







    }












































































































































