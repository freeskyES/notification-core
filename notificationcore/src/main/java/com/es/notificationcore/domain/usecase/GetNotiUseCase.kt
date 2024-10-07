package com.es.notificationcore.domain.usecase

import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.data.noti.NotiRepository
import javax.inject.Inject

class GetNotiUseCase
@Inject
constructor(
    private val repository: NotiRepository,
) {
    suspend fun execute(): List<Noti> {
        return repository.getAllNotifications()
    }
}
