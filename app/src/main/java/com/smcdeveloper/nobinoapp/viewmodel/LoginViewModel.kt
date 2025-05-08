package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor():ViewModel() {

    private val _isUserLogin = MutableStateFlow<Boolean>(false)
    val isUserLogin : StateFlow<Boolean> = _isUserLogin.asStateFlow()

    fun updateLoging(isloging:Boolean)
    {

        _isUserLogin.value=isloging
      //  Log.d("log","viewModel login value is"+_isLoging.value)





    }







}