package com.smcdeveloper.nobinoapp.di

import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.ProductDetailsApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProductDetailsApiInterfaceModule {

    @Singleton
    @Provides
    fun provideProductDetailsApiService(retrofit: Retrofit) : ProductDetailsApiInterface =
        retrofit.create(ProductDetailsApiInterface::class.java)








}