package com.smcdeveloper.nobinoapp.data.source


import android.util.Log
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smcdeveloper.nobinoapp.data.model.favorit.Favorite
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.repository.FavoriteRepository
import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import retrofit2.HttpException
import java.io.IOException

class FavoriteDataSource(

    private val repository:FavoriteRepository,

    var pageNum :Int=0,
    var pagesize: Int =20


    //  val specialId:Int

) :PagingSource <Int,Favorite.FavoriteData.Item>()


{




    override fun getRefreshKey(state: PagingState<Int, Favorite.FavoriteData.Item>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }


    }


     suspend fun load1(params: LoadParams<Int>): LoadResult<Int, Favorite.FavoriteData.Item> {
        val offset = params.key ?: 0 // Default to 0 if no key provided
        var size =20


        size = if(params.loadSize==60) {
            20
        } else
            params.loadSize // The number of items to load per page

        //size1=20
        Log.d("page" ,"offset is:${offset} size is: ${size}")


        return try {
            // Make the API call with the correct parameters
            Log.d("page" ,"current offset is:${offset} size is: ${size}")


            val response = repository.getUserFavorites(
                auth = "Bearer $USER_TOKEN",
                size = size,
                pageNum = pageNum




            )

            // Extract the data from the response
            val dataMovie = response.data?.favoritData

            if (response.data?.success == true && dataMovie != null) {
                // Log successful responses for debugging purposes
                Log.d("NobinoApp", "PAGING3 Success: ${dataMovie.items}")

                // Return a successful LoadResult.Page
                val nextOffset = if (response.data.favoritData.size!! < size ) null else offset + size
                LoadResult.Page(
                    data = dataMovie.items?.filterNotNull() ?: emptyList(),

                    prevKey = if (offset == 0) null else offset - size,


                 //   nextKey = if ((offset + size) >= (dataMovie.total ?: 0)) null else offset + size
                    nextKey = nextOffset
                )
            } else {
                // Log user-friendly error messages
                Log.e("NobinoApp", "Error: ${response.data?.userMessage ?: "Unknown error"}")
                LoadResult.Error(Exception(response.data?.userMessage ?: "Unknown error"))
            }
        } catch (exception: IOException) {
            Log.e("NobinoApp", "Network error: ${exception.message}", exception)
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("NobinoApp", "HTTP error: ${exception.message}", exception)
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            Log.e("NobinoApp", "Unexpected error: ${exception.message}", exception)
            LoadResult.Error(exception)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Favorite.FavoriteData.Item> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        Log.d("PagingSource", "🚀 Loading movies from offset=$offset, limit=$limit")

        return try {
            val response = repository.getUserFavorites(

               auth = "Bearer $USER_TOKEN",
                size = limit,
                pageNum = offset,


            )
            Log.d("PagingSource", "✅ Received ${response.data?.favoritData!!.size} items from API")

            // ❗ Ensure API returns expected data
            if (response.data.favoritData.items.isNullOrEmpty()) {
                Log.d("PagingSource", "⛔ No more data available, stopping pagination.")
                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null) // Stop pagination
            }

            val nextOffset = if (response.data.favoritData.items.size < limit) {
                Log.d("PagingSource", "🚨 API returned less than requested, setting nextKey=null")
                null
            } else {
                offset + limit
            }

            LoadResult.Page(
                data = response.data.favoritData.items.filterNotNull() ?: emptyList(),
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = nextOffset
            )
        } catch (e: Exception) {
            Log.e("PagingSource", "❌ Error loading movies at offset=$offset: ${e.message}", e)
            LoadResult.Error(e)
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




