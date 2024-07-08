package com.kltn.ecodemy.di

import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.domain.repository.ZaloSDK
import com.kltn.ecodemy.data.impl.ZaloSDKImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ZaloModule {

    @Provides
    @Singleton
    fun provideZaloSDK(
        ecodemyApi: EcodemyApi
    ): ZaloSDK = ZaloSDKImpl(
        ecodemyApi = ecodemyApi
    )
}
