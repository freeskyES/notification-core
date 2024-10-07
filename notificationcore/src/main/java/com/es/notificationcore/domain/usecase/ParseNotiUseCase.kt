package com.es.notificationcore.domain.usecase

import android.service.notification.StatusBarNotification
import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.data.noti.service.NotiService
import com.es.notificationcore.data.noti.service.chat.ChatServiceFactory
import javax.inject.Inject

class ParseNotiUseCase
    @Inject
    constructor(
        private val notiService: NotiService,
        private val chatServiceFactory: ChatServiceFactory,
    ) {
        fun execute(sbn: StatusBarNotification): Noti? =
            if (notiService.canParsing(sbn)) {
                val noti = notiService.parseNoti(sbn)

                chatServiceFactory
                    .create(noti.appId)
                    ?.let {
                        if (it.canParsing(noti)) {
                            val baseNoti = it.parseChatNoti(noti)
                            noti.update(baseNoti)
                        }
                    }
                noti
            } else {
                null
            }
    }
