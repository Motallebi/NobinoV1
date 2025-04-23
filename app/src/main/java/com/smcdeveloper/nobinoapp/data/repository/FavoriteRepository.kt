package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.favorit.Favorite
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.model.profile.UpdateUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.UserInfo
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.ProfileApiInterface
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val api: ProfileApiInterface): BaseApiResponse2() {


    suspend fun getUserFavorites(size:Int,pageNum:Int): NetworkResult<Favorite> {

        val result = safeApiCall {


            api.getUserFavorits(size=size, page = pageNum)


        }

        Log.d("user", "Api call")


        return result
    }


}







































































































