package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.model.profile.LoginRequest
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.model.profile.UpdateUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

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
        @Field("ref_number") refNumber: String,
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







    @GET("api/products/{productId}")
    suspend fun getProductDetailInfo(

        @Path("productId") id: Int,
        @Header("Authorization") auth: String =""


    ):Response<ProductModel>






   // @FormUrlEncoded
    @GET("/api/v2/users/user-info")
    suspend fun getUserInfo(
        @Header("Authorization") auth: String =""








    ) :Response<UserInfo>




    //@FormUrlEncoded
    @PATCH("/api/v2/users/user-info/{id}")
    suspend fun updateUserInfo(
        @Path("id") userId :String,
        @Body updateRequest: UpdateUserProfileRequest






    ) :Response<UserInfo>

















}









