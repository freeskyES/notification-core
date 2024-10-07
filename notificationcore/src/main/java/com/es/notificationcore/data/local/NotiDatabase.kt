package com.es.notificationcore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.es.notificationcore.data.noti.local.entity.NotiEntity

@Database(entities = [NotiEntity::class], version = 1, exportSchema = false)
abstract class NotiDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotiDao
}
