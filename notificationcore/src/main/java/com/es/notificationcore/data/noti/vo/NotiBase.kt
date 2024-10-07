package com.es.notificationcore.data.noti.vo

data class NotiBase(
    val title: String,
    val subTitle: String,
    val content: String,
    val notiAt: Long,
    val sender: String? = null,
)
