package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.avatar.Avatar
import com.smcdeveloper.nobinoapp.data.model.favorit.Favorite
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentHistory
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentRequest
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentResponse
import com.smcdeveloper.nobinoapp.data.model.prducts.ProductModel
import com.smcdeveloper.nobinoapp.data.model.profile.ActiveUserProfile
import com.smcdeveloper.nobinoapp.data.model.profile.LoginRequest
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.model.profile.NewUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.ProfileActivationRequest
import com.smcdeveloper.nobinoapp.data.model.profile.ProfileResponse
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
import retrofit2.http.Query

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


    @POST("api/subscriptions")
    suspend fun postUserPayment(
        @Header("Authorization") auth: String ="",
        @Body paymentRequest: PaymentRequest






    ): Response<PaymentResponse>






















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





    //@FormUrlEncoded
    @POST("/api/profile")
    suspend fun makeNewProfile(
        @Header("Authorization") auth: String ="",
        @Body addNewProfile: NewUserProfileRequest






    ) :Response<ProfileResponse>






    @POST("/api/profile")
    suspend fun profileActivation(
        @Header("Authorization") auth: String ="",
        @Body profileActivation: ProfileActivationRequest






    ) :Response<ProfileResponse>




    @POST("/api/profile/activate")
    suspend fun activeCurrentProfile(
        @Header("Authorization") auth: String ="",
        @Body profileActivation: ProfileActivationRequest






    ) :Response<ProfileResponse>




















    @GET("/api/v2/user-interactions")
    suspend fun getUserFavorits(
        @Header("Authorization") auth: String ="",

    @Query("size") size: Int=20,
    @Query("page") page: Int=1,
    @Query("type") type: String="BOOKMARK",









    ) :Response<Favorite>





    @GET("/api/avatar")
    suspend fun getAvatars(


    ) :Response<Avatar>




    @GET("/api/profile")
    suspend fun getUserProfile(
        @Header("Authorization") auth: String ="",
        @Query("page") page: Int=1,
        @Query("size") size: Int=10,










        ) :Response<List<ActiveUserProfile>>





    @GET("/api/v2/users/payments")
    suspend fun getUserPaymentHistory(
        @Header("Authorization") auth: String ="",
        @Query("page") page: String,
        @Query("size") size: String,











        ) :Response<PaymentHistory>


















}









