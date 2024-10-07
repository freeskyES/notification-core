package com.es.notificationcore.data.noti.service.chat

import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.data.noti.service.ChatService
import com.es.notificationcore.data.noti.vo.NotiBase

class WhatsAppChatService : ChatService {
    override fun canParsing(noti: Noti): Boolean = true

    override fun parseChatNoti(noti: Noti): NotiBase = noti.base
}
