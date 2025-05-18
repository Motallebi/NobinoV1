package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.search.Countries
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.SearchApi
import com.smcdeveloper.nobinoapp.data.source.SearchDataSource
import com.smcdeveloper.nobinoapp.data.source.SearchDataSource1
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(val api:SearchApi) : BaseApiResponse2(){


    ///test search

   // MutableStateFlow<PagingData<MovieResult.DataMovie.Item>>(PagingData.empty())

    fun searchProduct(query: String): Flow<PagingData<MovieResult.DataMovie.Item>> {
        return Pager(
            config = PagingConfig(pageSize = 10,
                initialLoadSize = 10


            ),
            pagingSourceFactory = {
                SearchDataSource1( api = api,
                    tagName = "",
                    categoryName = listOf("MOVIE,SERIES"),
                    name=query






                )
            }
        ).flow
    }


























    suspend fun fetchMovies(tag: String?,categoty:List<String>?, size: Int,offset: Int,countries:String?,name:String?,persons:String?



    ): NetworkResult<MovieResult> {
        //val tagstring = tag.substring(1, tag.length - 1)
        val result = safeApiCall {


            api.getSearchMoviesWithPages(tags = tag,
                categoris = categoty, size = size, offset = offset, countries = countries,
                name = name,
                persons = persons



            )


        }
        Log.d(NOBINO_LOG_TAG, "----fetchMovieTest-----${tag}")
        Log.d(NOBINO_LOG_TAG, "----fetchMovieTest-----")
        Log.d("fetchMovieTest", "----fetchMovieTest-----${tag}")

        return result


    }


























}