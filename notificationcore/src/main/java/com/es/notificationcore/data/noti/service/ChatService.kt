package com.es.notificationcore.data.noti.service

import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.data.noti.vo.NotiBase

interface ChatService {
    fun canParsing(noti: Noti): Boolean

    fun parseChatNoti(noti: Noti): NotiBase
}
