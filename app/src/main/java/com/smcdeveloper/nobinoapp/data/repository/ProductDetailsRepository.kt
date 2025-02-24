package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.model.prducts.BookMarKRequest
import com.smcdeveloper.nobinoapp.data.model.prducts.BookMark
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.model.search.Actor
import com.smcdeveloper.nobinoapp.data.model.search.PersonInfo
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.GeneralBaseApiResponse
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.ProductDetailsApiInterface
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import javax.inject.Inject

class ProductDetailsRepository @Inject constructor(
    private val api: ProductDetailsApiInterface,





): BaseApiResponse2() {


  /*  suspend fun getProductDetails(productId: Int, auth: String): NetworkResult<ProductModel> {
        val result= safeApiCall(
            apiCall = { api.getProductDetailInfo(productId, auth) },
            parseResponse = { it?.data }
        )



        return result
    }*/


    suspend fun getProductDetails(productId: Int, auth: String): NetworkResult<ProductModel> {
        Log.d("getProductDetails", "Fetching product details for productId: $productId, auth: $auth")

        val result = safeApiCall(
            apiCall = { api.getProductDetailInfo(productId, auth) },
          //  parseResponse = { it?.data }
        )

        // Log the result state
        when (result) {
            is NetworkResult.Loading -> {
                Log.d("getProductDetails", "API call is in Loading state.")
            }
            is NetworkResult.Success -> {
                Log.d("getProductDetails", "API call succeeded. Data: ${result.data}")
            }
            is NetworkResult.Error -> {
                Log.e("getProductDetails", "API call failed. Error: ${result.message}")
            }
        }

        return result
    }



    suspend fun getActorDetails(ids:Int):NetworkResult<Actor> =
       safeApiCall {

           api.getActorDetails(ids)



       }







    suspend fun getProductDetails(productId: Int): NetworkResult<ProductModel> {
       // Log.d("getProductDetails", "Fetching product details for productId: $productId, auth: $auth")

        val result = safeApiCall(
            apiCall = { api.getProductDetailInfo(productId) },
            //  parseResponse = { it?.data }
        )

        // Log the result state
        when (result) {
            is NetworkResult.Loading -> {
                Log.d("getProductDetails", "API call is in Loading state.")
            }
            is NetworkResult.Success -> {
                Log.d("getProductDetails", "API call succeeded. Data: ${result.data}")
            }
            is NetworkResult.Error -> {
                Log.e("getProductDetails", "API call failed. Error: ${result.message}")
            }
        }

        return result
    }












    /* suspend fun fetchRelatedMovies(productId: Int, tags: String): NetworkResult<MovieResult> {
         return safeApiCall(
             apiCall = { api.getRelatedProducts(productId, tags) },
           //  parseResponse = { it?.data }

         )

     }*/


    suspend fun fetchRelatedMovies(tags: String): NetworkResult<MovieResult> {
        Log.d("fetchRelatedMovies", "Initiating API call for related movies. Product ID:  Tags: $tags")

        val result = safeApiCall(
            apiCall = {
                val response = api.getRelatedProducts(tags = tags)
                Log.d("fetchRelatedMovies", "Raw API response received: $response")
                response
            },
           /* parseResponse = { parsedResponse ->
                Log.d("fetchRelatedMovies", "Parsed response: $parsedResponse")
                parsedResponse?.data // Extract the `data` field from the response
            }*/
        )

        // Log the result based on the state of `NetworkResult`
        when (result) {
            is NetworkResult.Loading -> {
                Log.d("fetchRelatedMovies", "API call is in Loading state.")
            }
            is NetworkResult.Success -> {
                Log.d("fetchRelatedMovies", "API call succeeded. Related movies: ${result.data}")
            }
            is NetworkResult.Error -> {
                Log.e("fetchRelatedMovies", "API call failed. Error: ${result.message}")
            }
        }

        return result
    }


    suspend fun fetchRelatedMovies(tags: List<String>): NetworkResult<MovieResult> {
        Log.d("fetchRelatedMovies", "Initiating API call for related movies. Product ID:  Tags: $tags")

        val result = safeApiCall(
            apiCall = {
                val response = api.getRelatedProductsByTagList(tags = tags)
                Log.d("fetchRelatedMovies", "Raw API response received: $response")
                response
            },
            /* parseResponse = { parsedResponse ->
                 Log.d("fetchRelatedMovies", "Parsed response: $parsedResponse")
                 parsedResponse?.data // Extract the `data` field from the response
             }*/
        )

        // Log the result based on the state of `NetworkResult`
        when (result) {
            is NetworkResult.Loading -> {
                Log.d("fetchRelatedMovies", "API call is in Loading state.")
            }
            is NetworkResult.Success -> {
                Log.d("fetchRelatedMovies", "API call succeeded. Related movies: ${result.data}")
            }
            is NetworkResult.Error -> {
                Log.e("fetchRelatedMovies", "API call failed. Error: ${result.message}")
            }
        }

        return result
    }



    suspend fun getSeriesEpisodes(id: Int): NetworkResult<MovieResult> =
        safeApiCall {
            api.getSeriesEpisodes(id)
        }



   suspend fun saveBookMark(bookMarKRequest: BookMarKRequest,auth: String):NetworkResult<BookMark> =




       safeApiCall {

           api.saveBookMark(
               auth = auth,
               bookMark =bookMarKRequest
           )






       }














}









































































































































