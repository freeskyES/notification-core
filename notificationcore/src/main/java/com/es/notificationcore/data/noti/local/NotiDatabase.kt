package com.es.notificationcore.data.noti.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.es.notificationcore.data.noti.local.entity.NotiEntity

@Database(entities = [NotiEntity::class], version = 1, exportSchema = false)
abstract class NotiDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao

    companion object {
        // 이 클래스에서는 실제 데이터베이스 인스턴스를 관리하지 않음
        // Hilt를 통해 주입된 Database 인스턴스를 사용할 예정
    }
}
