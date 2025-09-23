package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.util.Constants.USER_LOGIN_STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor():ViewModel() {

    private val _isUserLogin = MutableStateFlow<Boolean>(false)
    val isUserLogin : StateFlow<Boolean> = _isUserLogin.asStateFlow()
    private val initialTimeSeconds = 300L
    private val _loginTimer = MutableStateFlow<Long>(initialTimeSeconds)
    val loginTimer : StateFlow<Long> = _loginTimer.asStateFlow()
    private var timerJob:Job?=null

    fun setLoginTimer()
    {

     timerJob= viewModelScope.launch {
         timerJob?.cancel()
         _loginTimer.value=initialTimeSeconds

            while (_loginTimer.value>0)
            {

                Log.d("timer","timer is ...${_loginTimer.value}")
                delay(1000)
                _loginTimer.value--




            }







        }





    }

    fun resetLoginTimer()
    {
        _loginTimer.value=initialTimeSeconds
        timerJob?.cancel()
        timerJob=null
        setLoginTimer()

    }

    fun stopLoginTimer()
    {
        timerJob?.cancel()
        timerJob=null



    }



    override fun onCleared() {
        timerJob?.cancel()
        timerJob=null
    }


    fun updateLoging(isloging:Boolean)
    {

       _isUserLogin.value=isloging
        USER_LOGIN_STATUS=isloging

      //  Log.d("log","viewModel login value is"+_isLoging.value)





    }







}