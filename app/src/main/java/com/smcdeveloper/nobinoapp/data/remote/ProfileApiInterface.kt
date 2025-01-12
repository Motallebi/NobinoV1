package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.ResponseResult
import com.smcdeveloper.nobinoapp.data.model.profile.LoginRequest
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ProfileApiInterface {


    //@FormUrlEncoded
    @POST("/realms/armaghan/protocol/openid-connect/token")
    suspend fun login1(
        @Body login : LoginRequest
    ) : Response<LoginResponse>



    @FormUrlEncoded
    @POST("/realms/armaghan/protocol/openid-connect/token")
    suspend fun getOtp(
        @Field("client_id") clientId: String="nobino-direct",
        @Field("grant_type") grantType: String="password",
        @Field("client_secret") clientSecret: String="wbUe9HHhsuv0QtPxJxdzGEkOrNG0w6ab",
        @Field("mobile") mobile: String
    ): Response<LoginResponse>



    @FormUrlEncoded
    @POST("/realms/armaghan/protocol/openid-connect/token")
    suspend fun validateOtp(
        @Field("client_id") clientId: String="nobino-direct",
        @Field("grant_type") grantType: String="password",
        @Field("client_secret") clientSecret: String="wbUe9HHhsuv0QtPxJxdzGEkOrNG0w6ab",
        @Field("mobile") mobile: String,
        @Field("ref_number") ref_number: String,
        @Field("otp") otp: String,



    ): Response<LoginResponse>




    @FormUrlEncoded
    @POST("/realms/armaghan/protocol/openid-connect/token")
    suspend fun getValidationToken(
        @Field("client_id") clientId: String="nobino-direct",
        @Field("grant_type") grantType: String="password",
        @Field("client_secret") clientSecret: String="wbUe9HHhsuv0QtPxJxdzGEkOrNG0w6ab",
        @Field("mobile") mobile: String,
        @Field("ref_number") ref_number: String,
        @Field("otp") otp: String,



    ): Response<LoginResponse>









}