package com.kltn.ecodemy.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant
import com.kltn.ecodemy.ui.theme.Primary3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun providesNotificationBuilder(
        @ApplicationContext context: Context,
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, Constant.NOTIFICATION_CHAT_CHANNEL)
            .setSmallIcon(R.drawable.devices)
            .setColor(Primary3.toArgb())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    @Provides
    @Singleton
    fun providesNotificationManager(
        @ApplicationContext context: Context,
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constant.NOTIFICATION_CHAT_CHANNEL,
                "Chat",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }
}