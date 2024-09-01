package com.es.notificationcore.domain.repository

import com.es.notificationcore.data.model.NotificationEntity

interface NotificationRepository {
    suspend fun saveNotification(notification: NotificationEntity)

    suspend fun getAllNotifications(): List<NotificationEntity>
}
