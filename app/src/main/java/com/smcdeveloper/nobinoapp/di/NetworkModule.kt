package com.smcdeveloper.nobinoapp.di

import android.util.Log
import com.smcdeveloper.nobinoapp.util.Constants.BASE_URL
import com.smcdeveloper.nobinoapp.util.Constants.PURCHASE_URL
import com.smcdeveloper.nobinoapp.util.Constants.TIMEOUT_IN_SECOND
import com.smcdeveloper.nobinoapp.util.Constants.USER_PROFILE_ID
import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun interceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    @Singleton
    @Named("ins1")
    internal fun interceptor2()= interceptor().apply {
         setLevel(HttpLoggingInterceptor.Level.BODY)


    }






    @Provides
    @Singleton
    fun  provideOkHttpWithoutAuth(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()

            chain.proceed(request.build())

        }
        .addInterceptor(interceptor())
        .build()




    @Provides
    @Singleton
    @Named("AUTH")
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()

            if (chain.request().url.toString().startsWith(BASE_URL)) {

                Log.d("net","user token $USER_TOKEN")


                if(USER_TOKEN!="USER_TOKEN" && USER_TOKEN.length>2) {
                    Log.d("net","user token step1 $USER_TOKEN")
                    Log.d("net","user token step1")




                    request
                        .addHeader("Authorization","Bearer $USER_TOKEN")
                    Log.d("Network", "user token is: $USER_TOKEN userf profile is $USER_PROFILE_ID")

                }
                else if( USER_PROFILE_ID !="USER_PROFILE_ID")
                {
                    Log.d("net","user token step2")
                    request
                        .addHeader("Authorization", "Bearer $USER_TOKEN")
                        .addHeader("Profile-Id", USER_PROFILE_ID)
                       // .addHeader("Profile-Id2", USER_PROFILE_ID)


                }
                else
                    Log.d("net","user token step3")
                  //  request.removeHeader("Authorization")



            }

            chain.proceed(request.build())
        }
        .addInterceptor(interceptor())
        .build()








    @Provides
    @Singleton
    fun provideRetrofit(@Named("AUTH")okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @Named("AUTH_RETROFIT")

    fun provideAuthRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()








    @Provides
    @Singleton
    @Named("purchaseUrl")
    fun providePurchaseRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(PURCHASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()











}