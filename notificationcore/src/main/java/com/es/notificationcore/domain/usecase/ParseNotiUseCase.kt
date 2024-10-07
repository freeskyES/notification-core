package com.es.notificationcore.domain.usecase

import android.service.notification.StatusBarNotification
import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.data.noti.service.NotiService
import com.es.notificationcore.data.noti.service.chat.ChatServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ParseNotiUseCase
@Inject
constructor(
    private val notiService: NotiService,
    private val chatServiceFactory: ChatServiceFactory,
) {
    suspend fun execute(sbn: StatusBarNotification): Noti? = withContext(Dispatchers.Default) {
        if (!notiService.canParsing(sbn)) return@withContext null

        val noti = notiService.parseNoti(sbn)

        chatServiceFactory.create(noti.appId)?.run {
            if (canParsing(noti)) {
                val baseNoti = parseChatNoti(noti)
                noti.update(baseNoti)
            }
        }
        noti
    }
}
