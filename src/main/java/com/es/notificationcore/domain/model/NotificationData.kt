package com.es.notificationcore.domain.model

data class NotificationData(
    val id: Long = 0,
    val title: String,
    val content: String,
    val timestamp: Long,
)
