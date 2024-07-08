package com.kltn.ecodemy.data.api

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kltn.ecodemy.MainActivity
import com.kltn.ecodemy.R
import com.kltn.ecodemy.constant.Constant.NOTIFICATION_CHAT_CHANNEL
import com.kltn.ecodemy.constant.formatToString
import com.kltn.ecodemy.data.impl.PreferenceKey
import com.kltn.ecodemy.domain.models.NotificationMapper
import com.kltn.ecodemy.domain.repository.AccessToken
import com.kltn.ecodemy.domain.repository.DataStorePreferences
import com.kltn.ecodemy.domain.repository.RealmDataProcess
import com.kltn.ecodemy.ui.theme.Primary3
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.Realm
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

@ExperimentalLayoutApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class PushNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var realmDataProcess: RealmDataProcess

    private val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHAT_CHANNEL)
        .setColor(Primary3.toArgb())
        .setSmallIcon(R.drawable.logo_outlined)
        .setAutoCancel(true)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("ReceivedMessage", message.data.toString())
        Log.d("ReceivedMessage", message.notification?.title.toString())
        val notification = message.notification
        val data = message.data
        if (notification != null) {
            showNotification(title = notification.title ?: "", content = notification.body ?: "")
            Log.d("NotificationData", data.isEmpty().toString())
            if (data.isNotEmpty()) {
                runBlocking {
                    Log.d("Notification", "Add notification")
                    realmDataProcess.insertData(
                        notificationMapper = NotificationMapper().apply {
                            image = data["image"] ?: ""
                            time = LocalDateTime.now().toString()
                            title = notification.title ?: ""
                            content = notification.body ?: ""
                        }
                    )
                }
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("NewToken", token)
    }

    private fun showNotification(
        title: String,
        content: String,
    ) {
        val intent = Intent(this, MainActivity::class.java)

        val notificationID = Random.nextInt()

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager =
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHAT_CHANNEL,
                "Chat",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Chat description"
                enableLights(true)
                lightColor = Primary3.toArgb()
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationBuilder.setContentText(content)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationID, notificationBuilder.build())
    }
}