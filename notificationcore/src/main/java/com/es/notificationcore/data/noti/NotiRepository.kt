package com.es.notificationcore.data.noti

interface NotiRepository {
    suspend fun saveNotification(noti: Noti)

    suspend fun getAllNotifications(): List<Noti>
}
