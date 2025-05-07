package com.smcdeveloper.nobinoapp.data.source


import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.LOG_TAG
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smcdeveloper.nobinoapp.data.model.favorit.Favorite
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.repository.ProfileRepository
import com.smcdeveloper.nobinoapp.data.repository.SearchRepository
import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import retrofit2.HttpException
import java.io.IOException

class SearchDataSource(

    private val repository:SearchRepository,
    val tagName:String?=null,
    val categoryName:List<String>?=null ,
    val countries:String?=null,
    val name: String?=null,
   // var offset :Int=0,
    var size: Int=10,
    var persons:String?=null


    //  val specialId:Int

) :PagingSource <Int,MovieResult.DataMovie.Item>()


{
   var loadedItems=0



    override fun getRefreshKey(state: PagingState<Int, MovieResult.DataMovie.Item>): Int? {
        Log.d("PagingSource","refresh...")

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(size) ?: anchorPage?.nextKey?.minus(size)

            Log.d("PagingSource", "🚀 Loading movies from  perv:${anchorPage?.prevKey} + next:${anchorPage?.nextKey} )")
        }


    }


     suspend fun load1(params: LoadParams<Int>): LoadResult<Int, MovieResult.DataMovie.Item> {
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


            val response = repository.fetchMovies(
                size = size,
                tag = tagName,
                categoty = categoryName,
                offset = offset,
                countries = countries,
                name = name,
                persons = persons



            )

            // Extract the data from the response
            val dataMovie = response.data?.movieInfo

            if (response.data?.success == true && dataMovie != null) {
                // Log successful responses for debugging purposes
                Log.d("NobinoApp", "PAGING3 Success: ${dataMovie.items}")

                // Return a successful LoadResult.Page
                val nextOffset = if (response.data.movieInfo.size!! < size ) null else offset + size
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

     suspend fun load2(params: LoadParams<Int>): LoadResult<Int, MovieResult.DataMovie.Item> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        Log.d("PagingSource", "🚀 Loading movies from offset=$offset, limit=$limit")

        return try {
            val response = repository.fetchMovies(

                size = limit,
                tag = tagName,
                categoty = categoryName,
                offset = offset,
                countries = countries,
                name = name,
                persons = persons

            )
            Log.d("PagingSource", "✅ Received ${response.data?.movieInfo!!.size} items from API")
            Log.d("PagingSource", "Loaded items: ${response.data.movieInfo.items.toString()}")

            // ❗ Ensure API returns expected data
            if (response.data.movieInfo.items.isNullOrEmpty()) {
                Log.d("PagingSource", "⛔ No more data available, stopping pagination.")
                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null) // Stop pagination
            }

            val nextOffset = if (response.data.movieInfo.items.size < limit) {
                Log.d("PagingSource", "🚨 API returned less than requested, setting nextKey=null")
                null
            } else {
                offset + limit
            }

            LoadResult.Page(
                data = response.data.movieInfo.items.filterNotNull() ?: emptyList(),
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = nextOffset
            )
        } catch (e: Exception) {
            Log.e("PagingSource", "❌ Error loading movies at offset=$offset: ${e.message}", e)
            LoadResult.Error(e)
        }
    }


    suspend fun load3(params: LoadParams<Int>): LoadResult<Int, MovieResult.DataMovie.Item> {

        return try {
            val nextPageNumber = params.key ?: 1


            val response = repository.fetchMovies(
                tag = tagName,
                countries = countries,
                name = name,
                size = 20,
                categoty = categoryName,
                offset = nextPageNumber,
                persons = persons
            ).data


            LoadResult.Page(
                data = response?.movieInfo?.items!!.filterNotNull(

                ),
                prevKey = null,
                nextKey = nextPageNumber + 1
            )


        } catch (e: Exception) {
            Log.d("3636", "error:$e ")
            PagingSource.LoadResult.Error(e)
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



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult.DataMovie.Item> {

        Log.d("PagingSource1", "🚀 Loading......")
        Log.d("PagingSource", "🚀 Loading......key is:000-* ${params.key}")

         val pageNum = params.key ?: 0
         val limit = params.loadSize
        // offset=pageNum*size
        val offset = params.key ?: 0


        Log.d("PagingSource", "🚀 Loading movies from page=$pageNum, limit=$limit")
       // val nextPageNumber = params.key ?: 0

        return try {

            Log.d("PagingSource", "🔄 Loading page | offset = $offset, pageSize = $size")

            val response = repository.fetchMovies(
                tag = tagName,
                countries = countries,
                name = name,
                size = limit,
                categoty = categoryName,
                offset = offset,
                persons = persons






            ).data
          var totalItems =response!!.movieInfo!!.total

           loadedItems += response.movieInfo?.items?.size!!
            Log.d("PagingSource", "🔄 Loading items are $loadedItems")
            Log.d("PagingSource", "🔄 Loading total are $totalItems")



          val nextKey=  if (response.movieInfo.items.size!!<limit)  null else limit+offset



            Log.d("PagingSource", "✅ Loaded ${response.movieInfo.items.size} items at offset $offset  nextKey $nextKey"   )


            LoadResult.Page(
                data = response.movieInfo.items.filterNotNull(


                ),




                        //response.movieInfo.total
                prevKey =   if(offset == 0) null else offset-size,
                nextKey = nextKey
            )



        } catch (e: Exception) {
            Log.d("3636", "error:$e ")
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




