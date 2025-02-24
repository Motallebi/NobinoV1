package com.smcdeveloper.nobinoapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.search.Actor
import com.smcdeveloper.nobinoapp.data.model.search.PersonInfo
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProductDetailsRepository
import com.smcdeveloper.nobinoapp.ui.screens.search.FilterCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorsViewModel @Inject constructor(val repository: ProductDetailsRepository):ViewModel()

{


    private val _actor = MutableStateFlow<NetworkResult<Actor>>(NetworkResult.Loading())
    val actor: StateFlow<NetworkResult<Actor>> get() = _actor.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val loading: StateFlow<Boolean>   = _isLoading


    fun getActorDetail(actorId:Int)
    {
        viewModelScope.launch {

            _isLoading.value = true

            val result=  repository.getActorDetails(actorId)

            _actor.value=result
            _isLoading.value=false


        }






    }


























}