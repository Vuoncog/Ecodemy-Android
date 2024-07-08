package com.kltn.ecodemy.di

import com.kltn.ecodemy.data.repository.RealmDataProcessImpl
import com.kltn.ecodemy.domain.models.NotificationMapper
import com.kltn.ecodemy.domain.repository.RealmDataProcess
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun providesRealm(): Realm{
        val config = RealmConfiguration.Builder(
            schema = setOf(
                NotificationMapper::class
            )
        )
            .compactOnLaunch()
            .build()

        return Realm.open(config)
    }

    @Provides
    @Singleton
    fun providesRealmProcess(
        realm: Realm
    ): RealmDataProcess = RealmDataProcessImpl(realm)

}