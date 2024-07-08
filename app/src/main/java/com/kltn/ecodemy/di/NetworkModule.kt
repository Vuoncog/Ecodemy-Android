package com.kltn.ecodemy.di

import com.kltn.ecodemy.constant.Constant.BASE_URL
import com.kltn.ecodemy.constant.Constant.FIREBASE_MESSAGE_URL
import com.kltn.ecodemy.constant.Constant.RECOMMENDER_SYSTEM_URL
import com.kltn.ecodemy.constant.Constant.ZALO_URL
import com.kltn.ecodemy.data.api.EcodemyApi
import com.kltn.ecodemy.data.api.FirebaseCloudMessagingApi
import com.kltn.ecodemy.data.api.SystemApi
import com.kltn.ecodemy.data.api.ZalopayApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @Named("BaseOkHttp")
    fun providesOkHttpClient(): OkHttpClient {
        val spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()

        return OkHttpClient
            .Builder()
            .connectionSpecs(Collections.singletonList(spec))
            .callTimeout(10000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("Fcm")
    fun providesFcmRetrofit() = Retrofit.Builder()
        .baseUrl(FIREBASE_MESSAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("Zalo")
    fun providesZalopayRetrofit(
        @Named("BaseOkHttp") okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(ZALO_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("Ecodemy")
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("Recommender")
    fun providesRecommenderRetrofit() = Retrofit.Builder()
        .baseUrl(RECOMMENDER_SYSTEM_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesEcodemyApi(@Named("Ecodemy") retrofit: Retrofit) =
        retrofit.create(EcodemyApi::class.java)

    @Provides
    @Singleton
    fun providesRecommenderApi(@Named("Recommender") retrofit: Retrofit) =
        retrofit.create(SystemApi::class.java)

    @Provides
    @Singleton
    fun providesZalopayApi(@Named("Zalo") retrofit: Retrofit) =
        retrofit.create(ZalopayApi::class.java)

    @Provides
    fun providesFcmApi(@Named("Fcm") retrofit: Retrofit) =
        retrofit.create(FirebaseCloudMessagingApi::class.java)
}