package com.es.notificationcore.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.es.notificationcore.R

class NotificationHelper(
    private val context: Context,
) {
    companion object {
        const val CHANNEL_ID = "notification_service_channel"
        const val NOTIFICATION_ID = 2
        const val NOTIFICATION_CHANNEL = "Notification Service"
    }

    init {
        createNotificationChannel()
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    NOTIFICATION_CHANNEL,
                    NotificationManager.IMPORTANCE_DEFAULT,
                )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    fun createNotification(notificationIntent: Intent): Notification {
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        return NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setContentTitle("Notification Service")
            .setContentText("Processing notifications")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}
