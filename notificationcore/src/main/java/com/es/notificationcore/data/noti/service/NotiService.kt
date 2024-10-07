package com.es.notificationcore.data.noti.service

import android.service.notification.StatusBarNotification
import com.es.notificationcore.data.noti.Noti

interface NotiService {
    fun canParsing(sbn: StatusBarNotification): Boolean

    fun parseNoti(sbn: StatusBarNotification): Noti
}
