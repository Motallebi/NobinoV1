package com.smcdeveloper.nobinoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val repository: DataStoreRepository



):ViewModel() {


    companion object {
        const val USER_LANGUAGE_KEY = "USER_LANGUAGE_KEY"
        const val USER_TOKEN_KEY = "USER_TOKEN_KEY"
        const val USER_ID_KEY = "USER_ID_KEY"
        const val USER_PHONE_KEY = "USER_PHONE_KEY"
        const val USER_PASSWORD_KEY = "USER_PASSWORD_KEY"
        const val USER_FIRST_NAME_KEY = "USER_FIRST_NAME_KEY"
        const val USER_LAST_NAME_KEY = "USER_LAST_NAME_KEY"

        const val USER_ADDRESS_KEY = "USER_ADDRESS_KEY"
        const val USER_REF_KEY = "USER_REF_KEY"
        const val USER_EMAIL_KEY = "USER_EMAIL_KEY"
        const val USER_BD_KEY = "USER_BD_KEY"
        const val USER_LOGIN_STATUS_KEY = "USER_LOGIN_STATUS_KEY"





    }



    // StateFlow for observing user data
    private val _userToken = MutableStateFlow<String?>(null)
    val userToken: StateFlow<String?> get() = _userToken

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> get() = _userId

    private val _userPhone = MutableStateFlow<String?>(null)
    val userPhone: StateFlow<String?> get() = _userPhone

    private val _userFirstName = MutableStateFlow<String?>(null)
    val userFirstName: StateFlow<String?> get() = _userFirstName

    private val _userLastName = MutableStateFlow<String?>(null)
    val userLastName: StateFlow<String?> get() = _userLastName



    private val _userAddress = MutableStateFlow<String?>(null)
    val userAddress: StateFlow<String?> get() = _userAddress

    private val _userRefKey = MutableStateFlow<String?>(null)
    val userRefKey: StateFlow<String?> get() =  _userRefKey

    private val _userEmailKey = MutableStateFlow<String?>(null)
    val userEmailKey: StateFlow<String?> get() =  _userEmailKey

    private val _userBdateKey = MutableStateFlow<String?>(null)
    val userBdateKey: StateFlow<String?> get() =  _userBdateKey


    private val _userLoginStatus = MutableStateFlow<Boolean?>(null)
    val userLoginStatus: StateFlow<Boolean?> get() =  _userLoginStatus










    init {
        // Load initial values
        viewModelScope.launch {
            _userToken.value = repository.getString(USER_TOKEN_KEY)
            _userId.value = repository.getString(USER_ID_KEY)
            _userPhone.value = repository.getString(USER_PHONE_KEY)
            _userFirstName.value = repository.getString(USER_FIRST_NAME_KEY)
            _userLastName.value = repository.getString(USER_LAST_NAME_KEY)

            _userAddress.value = repository.getString(USER_ADDRESS_KEY)
            _userRefKey.value = repository.getString(USER_REF_KEY)
            _userEmailKey.value=repository.getString(USER_EMAIL_KEY)
            _userBdateKey.value=repository.getString(USER_BD_KEY)
            _userLoginStatus.value=repository.getBoolean(USER_LOGIN_STATUS_KEY)


        }
    }


























    fun saveUserPhone(value: String) {
        viewModelScope.launch {
            repository.putString(USER_PHONE_KEY, value)
        }
    }


    fun getUserPhoneNumber(): String? = runBlocking {
        repository.getString(USER_PHONE_KEY)
    }

    fun getUserRefKey(): String? = runBlocking {
        repository.getString(USER_REF_KEY)
    }

    fun saveUserRefKey(value: String) {
        viewModelScope.launch {
            repository.putString(USER_REF_KEY, value)
        }
    }


    fun getUserToken(): String? = runBlocking {
        repository.getString(USER_TOKEN_KEY)
    }





    fun getUserId(): String? = runBlocking {
        repository.getString(USER_ID_KEY)
    }

    fun getUserFirstName(): String? = runBlocking {
        repository.getString(USER_FIRST_NAME_KEY)
    }



    fun saveUserFirstName(value: String) {
        viewModelScope.launch {
            repository.putString(USER_FIRST_NAME_KEY, value)
        }
    }








    fun getUserLastName(): String? = runBlocking {
        repository.getString(USER_LAST_NAME_KEY)
    }


    fun saveUserLastName(value: String) {
        viewModelScope.launch {
            repository.putString(USER_LAST_NAME_KEY, value)
        }
    }






    fun saveUserToken(value: String) {
        viewModelScope.launch {
            repository.putString(USER_TOKEN_KEY, value)
        }
    }



    fun saveUserBirthDay(value: String) {
        viewModelScope.launch {
            repository.putString(USER_BD_KEY, value)
        }
    }



    fun getUserLoginStatus(): Boolean? = runBlocking {
        repository.getBoolean(USER_LOGIN_STATUS_KEY)
    }


    fun saveUserLoginStatus(value: Boolean) {
        viewModelScope.launch {
            repository.putBoolean(USER_LOGIN_STATUS_KEY, value)
        }
    }








}