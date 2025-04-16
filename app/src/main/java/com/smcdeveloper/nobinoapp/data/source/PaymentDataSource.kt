package com.smcdeveloper.nobinoapp.data.source


import android.annotation.SuppressLint
import android.util.Log
import androidx.collection.emptyLongSet
import androidx.paging.LOG_TAG
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smcdeveloper.nobinoapp.data.model.favorit.Favorite
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentHistory
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentResponse
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.remote.ProfileApiInterface
import com.smcdeveloper.nobinoapp.data.remote.SearchApi
import com.smcdeveloper.nobinoapp.data.repository.ProfileRepository
import com.smcdeveloper.nobinoapp.data.repository.SearchRepository
import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PaymentDataSource @Inject constructor(
    private val api: ProfileApiInterface,





    // var offset :Int=0,
    //var size: Int = 10


    //  val specialId:Int

) : PagingSource<Int, PaymentHistory.PaymentHistoryData.Payment>() {
    var loadedItems = 0


    override fun getRefreshKey(state: PagingState<Int, PaymentHistory.PaymentHistoryData.Payment>): Int? {
        Log.d("PagingSource", "refresh...")

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

            Log.d(
                "PagingSource",
                "🚀 Loading movies from  perv:${anchorPage?.prevKey} + next:${anchorPage?.nextKey} )"
            )
        }


    }


    /*  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult.DataMovie.Item>> {

          // val pageNum = params.key ?: 0
          // val limit = params.loadSize

          //Log.d("PagingSource", "🚀 Loading movies from page=$pageNum, limit=$limit")

          return try {
              val nextPageNumber = params.key ?: 1


              val response = repository.fetchMovies(
                  tag = tagName,
                  countries = countries,
                  name = name,
                  size = 20,
                  categoty = categoryName,
                  offset = nextPageNumber
              ).data


              LoadResult.Page(
                  data = response.movieInfo.items.filterNotNull(

                  ),
                  prevKey = null,
                  nextKey = nextPageNumber + 1
              )


          } catch (e: Exception) {
              Log.d("3636", "error:$e ")
              PagingSource.LoadResult.Error(e)
          }

      }
  */


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PaymentHistory.PaymentHistoryData.Payment> {

        Log.d("PagingSource1", "🚀 Loading......")
        Log.d("PagingSource", "🚀 Loading......key is:000-* ${params.key}")

        val pageNum = params.key ?: 1
        //val limit = params.loadSize
        // offset=pageNum*size
       // val offset = params.key ?: 0


        Log.d("PagingSource", "🚀 Loading payment from page=$pageNum")
        // val nextPageNumber = params.key ?: 0

        return try {

           // Log.d("PagingSource", "🔄 Loading page | offset = $offset, pageSize = $size")


            val response = api.getUserPaymentHistory(
                auth ="Bearer $USER_TOKEN" ,
                page = pageNum.toString(),
                size = "10"




            ).body()


            val endOfPaginationReached = response?.paymentHistoryData?.items!!.isEmpty()
            val nextKey = if (endOfPaginationReached) {
                Log.d("PagingSource", "🚨 API returned less than requested, setting nextKey=null")
                null
            } else {
               pageNum+1
            }

            /* var nextKey =pageNum
           // val endOfPaginationReached = response?.paymentHistoryData?.items!!.isEmpty()
            if (endOfPaginationReached) {
                Log.d("PagingSource", "🔄 Loading items are finished")
                nextKey=null



            }*/







          //  var totalItems = response!!.paymentHistoryData!!.total

          //  loadedItems += response.paymentHistoryData.items.size
           // Log.d("PagingSource", "🔄 Loading items are $loadedItems")
          //  Log.d("PagingSource", "🔄 Loading total are $totalItems")


            // val nextKey=  nextOffset


            //  Log.d("PagingSource", "✅ Loaded ${response.movieInfo.items.size} items at offset $offset  nextKey $nextKey"   )


            LoadResult.Page(
                data = response?.paymentHistoryData?.items!!.filterNotNull(


                ),


                //response.movieInfo.total
                prevKey = null,
                nextKey =nextKey
            )


        } catch (e: Exception)
        {
            Log.d("3636", "error:$e")
            PagingSource.LoadResult.Error(e)
        }


    }


    /* override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult.DataMovie.Item> {


         val offset = params.key ?: 0 // Start from offset 0 if no key is provided
         val limit = params.loadSize  // Size of each page to load


         return try {
            *//* val response1 = repository.fetchAllProductsByTag(

             size = 10,
            // tags =tagName.substring(1, tagName.length - 1),
                tags = "69f24e0b-b6c1-4094-88df-4db7476a2147",

                category = "SERIES",
                offset =offset
            )*//*

            val response= repository.fetchMovieTest(tagName,"")



            val dataMovie = response.data?.movieInfo
            Log.d("NobinoApp","PAGING3----------------${response.message}")
            Log.d("NobinoApp","PAGING3----------------")
            Log.d("NobinoApp","PAGING3----------------${dataMovie?.items.toString()}")



            if (response.data?.success == true && dataMovie != null) {
                LoadResult.Page(
                    data = dataMovie.items?.filterNotNull() ?: emptyList(),
                    prevKey = if (offset == 0) null else offset - limit,
                    nextKey = if ((offset + limit) >= (dataMovie.total ?: 0)) null else offset + limit
                )
            } else {
                LoadResult.Error(Exception(response.data?.userMessage ?: "Unknown error"))
            }
        }


        catch (exception: IOException) {
            LoadResult.Error(exception)


        }

        catch (exception: HttpException) {
            LoadResult.Error(exception)

        }















        }*/


}




