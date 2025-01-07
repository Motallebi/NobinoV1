package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.GeneralResponseResult
import com.smcdeveloper.nobinoapp.data.model.ResponseResult
import com.smcdeveloper.nobinoapp.data.model.profile.LoginRequest
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileApiInterface {

    @POST("/realms/armaghan/protocol/openid-connect/token")
    suspend fun login(
        @Body login : LoginRequest
    ) : Response<LoginResponse>









}