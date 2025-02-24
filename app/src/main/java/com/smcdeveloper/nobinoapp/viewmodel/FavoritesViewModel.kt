package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smcdeveloper.nobinoapp.data.model.favorit.Favorite
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.repository.FavoriteRepository
import com.smcdeveloper.nobinoapp.data.source.FavoriteDataSource
import com.smcdeveloper.nobinoapp.data.source.ProductBySpecialCategoryDataSource
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterCriteria
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(val repository: FavoriteRepository):ViewModel()

{

    private val _favoriteData = MutableStateFlow<PagingData<Favorite.FavoriteData.Item>>(PagingData.empty())
    val favoriteData: StateFlow<PagingData<Favorite.FavoriteData.Item>> = _favoriteData.asStateFlow()







  fun getUserFavorites(pageSize:Int,pageNumber: Int) : Flow<PagingData<Favorite.FavoriteData.Item>>
  {




        //  Log.d(NOBINO_LOG_TAG,"getmovie......")
      //    Log.d(NOBINO_LOG_TAG, "tag is......${tag.toString()}")

          return Pager(
              config = PagingConfig(
                  pageSize =20, // Page size
                  enablePlaceholders = false
              ),


              pagingSourceFactory = {

                 // Log.d(NOBINO_LOG_TAG, "Creating new MoviePagingSource with categoryId: ${tag.toString()}")

                  FavoriteDataSource(repository, pageNum = pageNumber, pagesize = pageSize  )


              }
          ).flow.cachedIn(viewModelScope)
      }









  }

















    















