package com.es.notificationcore.utils

import android.app.Notification
import android.service.notification.StatusBarNotification

class NotificationParser {
    fun parseTitle(sbn: StatusBarNotification?): String? = sbn?.notification?.extras?.getString(
        Notification.EXTRA_TITLE
    )

    fun parseSubTitle(sbn: StatusBarNotification?): String? = sbn?.notification?.extras?.getString(
        Notification.EXTRA_SUB_TEXT
    )

    fun parseContent(sbn: StatusBarNotification?): String? = sbn?.notification?.extras?.getString(
        Notification.EXTRA_TEXT
    )

    fun parseTimestamp(sbn: StatusBarNotification?): Long? = sbn?.postTime
}
