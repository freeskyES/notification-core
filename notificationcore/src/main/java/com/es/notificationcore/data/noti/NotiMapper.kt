package com.es.notificationcore.data.noti

import com.es.notificationcore.data.noti.local.entity.NotiEntity
import com.es.notificationcore.data.noti.vo.NotiBase
import java.util.UUID

fun Noti.toEntity(): NotiEntity =
    NotiEntity(
        id = 0,
        title = this.base.title,
        subTitle = this.base.subTitle,
        content = this.base.content,
        sender = this.base.sender,
        notiAt = this.base.notiAt,
        appId = this.appId,
        uuid = UUID.randomUUID().toString(),
    )

fun NotiEntity.toDomain(): Noti =
    Noti(
        id = this.uuid,
        appId = this.appId,
        base =
        NotiBase(
            title = this.title,
            subTitle = this.subTitle,
            content = this.content,
            notiAt = this.notiAt,
            sender = this.sender,
        ),
    )
