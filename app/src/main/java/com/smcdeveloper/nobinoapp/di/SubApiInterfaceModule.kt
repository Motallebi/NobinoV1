package com.smcdeveloper.nobinoapp.di

import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.SubsCriptionInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SubApiInterfaceModule {

    @Singleton
    @Provides
    fun provideSubApiService(retrofit: Retrofit) : SubsCriptionInterface =
        retrofit.create(SubsCriptionInterface::class.java)





}