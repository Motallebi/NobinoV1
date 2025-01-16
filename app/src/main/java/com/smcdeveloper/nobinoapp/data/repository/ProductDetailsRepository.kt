package com.smcdeveloper.nobinoapp.data.repository

import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.GeneralBaseApiResponse
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.ProductDetailsApiInterface
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import javax.inject.Inject

class ProductDetailsRepository @Inject constructor(
    private val api: ProductDetailsApiInterface,




): GeneralBaseApiResponse() {


    suspend fun getProductDetails(productId: Int, auth: String): NetworkResult<ProductModel> {
        return safeApiCall(
            apiCall = { api.getProductDetailInfo(productId, auth) },
            parseResponse = { it?.data }
        )
    }


    suspend fun fetchRelatedMovies(productId: Int, tags: String): NetworkResult<MovieResult> {
        return safeApiCall(
            apiCall = { api.getRelatedProducts(productId, tags) },
            parseResponse = { it?.data }

        )

    }


}









































































































































