package com.es.notificationcore.domain.usecase

import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.data.noti.NotiRepository
import javax.inject.Inject

class SaveNotiUseCase
@Inject
constructor(
    private val repository: NotiRepository,
) {
    suspend fun execute(noti: Noti) {
        repository.saveNotification(noti)
    }
}
