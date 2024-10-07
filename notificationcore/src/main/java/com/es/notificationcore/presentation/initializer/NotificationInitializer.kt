package com.es.notificationcore.presentation.initializer

import com.es.notificationcore.data.noti.Noti

interface NotificationInitializer {
    fun initializeAndStartService()

    fun startService()

    suspend fun getNotifications(): List<Noti>
}
