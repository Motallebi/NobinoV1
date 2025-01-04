package com.smcdeveloper.nobinoapp.data.source


import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.LOG_TAG
import com.smcdeveloper.nobinoapp.data.repository.HomeRepository
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import retrofit2.HttpException
import java.io.IOException

class ProductBySpecialCategoryDataSource(

    private val repository:HomeRepository,
    val tagName:String,
  //  val specialId:Int

) :PagingSource <Int,MovieResult.DataMovie.Item>()


{




    override fun getRefreshKey(state: PagingState<Int, MovieResult.DataMovie.Item>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }


        }




    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult.DataMovie.Item> {


        val offset = params.key ?: 0 // Start from offset 0 if no key is provided
        val limit = params.loadSize  // Size of each page to load


        return try {
           /* val response1 = repository.fetchAllProductsByTag(

             size = 10,
            // tags =tagName.substring(1, tagName.length - 1),
                tags = "69f24e0b-b6c1-4094-88df-4db7476a2147",

                category = "SERIES",
                offset =offset
            )*/

            val response= repository.fetchMovieTest(tagName)



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















        }



    }




