package com.smcdeveloper.nobinoapp.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.util.Constants.USER_FIRST_NAME
import com.smcdeveloper.nobinoapp.util.Constants.USER_ID
import com.smcdeveloper.nobinoapp.util.Constants.USER_LAST_NAME
import com.smcdeveloper.nobinoapp.util.Constants.USER_LOGIN_STATUS

import com.smcdeveloper.nobinoapp.util.Constants.USER_PHONE
import com.smcdeveloper.nobinoapp.util.Constants.USER_PROFILE_ID
import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AppConfig(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    dataStore: DataStoreViewModel = hiltViewModel()


)
{
    Log.d("net","Appconfig $USER_TOKEN" )

    getDataStoreVariables(dataStore)

    profileViewModel.fetchUserProfile(auth ="Bearer "+ dataStore.getUserToken().toString())


    Log.d("auth",dataStore.getUserToken().toString())






    LaunchedEffect(Dispatchers.Main) {

        profileViewModel.userProfile.collectLatest { loginResponse->

            when(loginResponse)
            {
                is NetworkResult.Success->{

                    Log.d("user","Success")

                    USER_LOGIN_STATUS=true
                    dataStore.saveUserLoginStatus(true)
















                }

                is NetworkResult.Error ->
                {
                   if(loginResponse.code==401)
                   {
                       Log.d("user","Error")

                       USER_LOGIN_STATUS =false
                       dataStore.saveUserToken("")



                   }









                }


                is NetworkResult.Loading ->

                {

                    Log.d("user","Laoding...")



                }
            }





        }





    }






 //   profileViewModel.refreshToken(USER_PHONE, USER_PASSWORD)


  /*  LaunchedEffect(Dispatchers.Main) {
        profileViewModel.loginResponse.collectLatest { loginResponse->
            when (loginResponse) {
                is NetworkResult.Success -> {
                    loginResponse.data?.let { user ->
                        if (user.token.isNotEmpty()) {
                            dataStore.saveUserToken(user.token)
                            dataStore.saveUserId(user.id)
                            dataStore.saveUserPhoneNumber(user.phone)
                            dataStore.saveUserPassword(USER_PASSWORD)

                            dataStore.saveUserName(user.name ?: "null")

                            getDataStoreVariables(dataStore)

                            Log.e("3636" , "refresh token")
                        }

                    }
                }
                else -> {}
            }
        }

    }
*/
}

private fun getDataStoreVariables(dataStore: DataStoreViewModel) {
  //  USER_LANGUAGE = dataStore.getUserLanguage()
   //dataStore.saveUserLanguage(USER_LANGUAGE)


    USER_PHONE = DigitHelper.digitByLocate(dataStore.getUserPhoneNumber().toString())
   // USER_PASSWORD = dataStore.getUserPassword().toString()
    USER_TOKEN = dataStore.getUserToken() ?:"USER_TOKEN"
    USER_ID = dataStore.getUserId().toString()
    USER_FIRST_NAME = dataStore.getUserFirstName().toString()
    USER_LAST_NAME = (dataStore.getUserLastName()?: "")
    USER_LOGIN_STATUS =dataStore.getUserLoginStatus()?:false
    USER_PROFILE_ID= dataStore.getUserCurrentProfileId() ?: "USER_PROFILE_ID"
}