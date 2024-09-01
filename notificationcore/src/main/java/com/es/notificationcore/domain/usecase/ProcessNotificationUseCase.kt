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

        suspend fun getAllNotifications(): List<NotificationData> {
            return repository.getAllNotifications().map { it.toDomain() }
        }
    }

private fun NotificationData.toEntity(): NotificationEntity =
    NotificationEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        timestamp = this.timestamp,
    )

fun NotificationEntity.toDomain(): NotificationData {
    return NotificationData(
        id = this.id,
        title = this.title,
        content = this.content,
        timestamp = this.timestamp
    )
}