package com.smcdeveloper.nobinoapp.di

import android.content.Context
import dagger.Provides
import com.smcdeveloper.nobinoapp.data.datastore.DataStoreRepository
import com.smcdeveloper.nobinoapp.data.datastore.DataStoreRepositoryImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(

        @ApplicationContext context: Context

   ):DataStoreRepository=DataStoreRepositoryImpl(context)









}