package com.es.notificationcore.utils

import android.content.Intent
import android.service.notification.StatusBarNotification
import com.es.notificationcore.domain.model.NotificationData

class NotificationParser {
    fun parse(sbn: StatusBarNotification?): NotificationData? {
        sbn ?: return null

        val title = sbn.notification.extras.getString("android.title")
        val content = sbn.notification.extras.getString("android.text")
        val timestamp = sbn.postTime

        return if (title != null && content != null) {
            NotificationData(
                title = title,
                content = content,
                timestamp = timestamp,
            )
        } else {
            null
        }
    }

    fun parse(intent: Intent?): NotificationData? {
        intent ?: return null

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val timestamp = intent.getLongExtra("timestamp", 0)

        return if (title != null && content != null) {
            NotificationData(
                title = title,
                content = content,
                timestamp = timestamp,
            )
        } else {
            null
        }
    }
}
