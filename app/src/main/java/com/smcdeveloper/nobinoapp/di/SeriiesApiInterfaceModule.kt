package com.smcdeveloper.nobinoapp.di

import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.SeriesInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SeriiesApiInterfaceModule {

    @Singleton
    @Provides
    fun provideSeriesApiService(retrofit: Retrofit) : SeriesInterface =
        retrofit.create(SeriesInterface::class.java)





}