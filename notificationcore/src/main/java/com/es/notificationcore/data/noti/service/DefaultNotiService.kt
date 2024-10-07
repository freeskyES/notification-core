package com.es.notificationcore.data.noti.service

import android.service.notification.StatusBarNotification
import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.data.noti.vo.NotiBase
import com.es.notificationcore.utils.NotificationParser
import java.util.UUID

class DefaultNotiService(
    val parser: NotificationParser,
) : NotiService {
    override fun canParsing(sbn: StatusBarNotification): Boolean {
        val packageName = sbn.packageName
        return packageName != "com.es.notificationdemo"
    }

    override fun parseNoti(sbn: StatusBarNotification): Noti =
        Noti(
            id = UUID.randomUUID().toString(),
            appId = sbn.packageName,
            base =
            NotiBase(
                title = parser.parseTitle(sbn) ?: "",
                subTitle = parser.parseSubTitle(sbn) ?: "",
                content = parser.parseContent(sbn) ?: "",
                notiAt = parser.parseTimestamp(sbn) ?: 0,
            ),
        )
}
