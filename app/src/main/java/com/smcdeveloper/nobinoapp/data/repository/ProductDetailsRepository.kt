package com.smcdeveloper.nobinoapp.data.repository

import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.ProductDetailsApiInterface
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import javax.inject.Inject

class ProductDetailsRepository @Inject constructor(
    private val api: ProductDetailsApiInterface,




): BaseApiResponse2() {


    suspend fun getProductDetails(productId: Int,auth:String): NetworkResult<ProductModel> =


        safeApiCall {

            api.getProductDetailInfo(productId,auth)

        }


}

































































































































