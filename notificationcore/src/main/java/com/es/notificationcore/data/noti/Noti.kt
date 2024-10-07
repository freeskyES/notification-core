package com.es.notificationcore.data.noti

import com.es.notificationcore.data.noti.vo.NotiBase

data class Noti(
    val id: String,
    val appId: String,
    var base: NotiBase,
) {
    fun update(base: NotiBase) {
        this.base = base
    }
}