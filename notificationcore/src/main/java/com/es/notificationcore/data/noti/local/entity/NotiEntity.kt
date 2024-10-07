package com.es.notificationcore.data.noti.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val uuid: String,
    val title: String,
    val subTitle: String,
    val content: String,
    val sender: String? = null,
    val appId: String,
    val notiAt: Long,
)
