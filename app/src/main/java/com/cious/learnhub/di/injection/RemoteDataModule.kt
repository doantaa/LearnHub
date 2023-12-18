package com.cious.learnhub.di.injection

import com.cious.learnhub.data.network.remote.ApiConfig
import com.cious.learnhub.data.network.remote.ApiService1
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideApiService():ApiService1 = ApiConfig.getApiService1()

}