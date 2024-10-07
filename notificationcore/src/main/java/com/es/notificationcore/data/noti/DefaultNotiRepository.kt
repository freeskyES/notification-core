package com.es.notificationcore.data.noti

import com.es.notificationcore.data.local.NotiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class DefaultNotiRepository
@Inject
constructor(
    private val notiDao: NotiDao,
) : NotiRepository {
    override suspend fun saveNotification(noti: Noti) {
        withContext(Dispatchers.IO) {
            Timber.d("saveNotification: $noti")
            notiDao.insertNotification(noti.toEntity())
        }
    }

    override suspend fun getAllNotifications(): List<Noti> =
        withContext(Dispatchers.IO) {
            notiDao.getAllNotifications().map { it.toDomain() }
        }
}
