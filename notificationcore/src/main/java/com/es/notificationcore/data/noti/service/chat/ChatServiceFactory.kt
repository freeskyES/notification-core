package com.es.notificationcore.data.noti.service.chat

import com.es.notificationcore.data.noti.service.ChatService

class ChatServiceFactory {
    fun create(packageName: String): ChatService? =
        when (packageName) {
            "com.kakao.talk" -> KakaoChatService()
            "com.slack.android.client" -> SlackChatService()
            "com.whatsapp" -> WhatsAppChatService()
            "com.instagram" -> InstagramChatService()
            else -> null
        }
}
