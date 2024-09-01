package com.es.notificationcore.data.repository

import com.es.notificationcore.data.local.NotificationDao
import com.es.notificationcore.data.model.NotificationEntity
import com.es.notificationcore.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NotificationRepositoryImpl
@Inject
constructor(
    private val notificationDao: NotificationDao,
) : NotificationRepository {
    override suspend fun saveNotification(notification: NotificationEntity) {
        withContext(Dispatchers.IO) {
            Timber.d("saveNotification: $notification")
            notificationDao.insertNotification(notification)
        }
    }

    override suspend fun getAllNotifications(): List<NotificationEntity> =
        withContext(Dispatchers.IO) {
            notificationDao.getAllNotifications()
        }
}
