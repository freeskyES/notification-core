package com.es.notificationcore.presentation.initializer

import com.es.notificationcore.domain.model.NotificationData

interface NotificationInitializer {
    fun initializeAndStartService()

    fun startService()

    suspend fun getNotifications(): List<NotificationData>
}
