package com.es.notificationcore.domain.usecase

import com.es.notificationcore.data.model.NotificationEntity
import com.es.notificationcore.domain.model.NotificationData
import com.es.notificationcore.domain.repository.NotificationRepository
import javax.inject.Inject

class ProcessNotificationUseCase
    @Inject
    constructor(
        private val repository: NotificationRepository,
    ) {
        suspend fun execute(notification: NotificationData) {
            val notificationEntity = notification.toEntity()
            repository.saveNotification(notificationEntity)
        }
    }

private fun NotificationData.toEntity(): NotificationEntity =
    NotificationEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        timestamp = this.timestamp,
    )
