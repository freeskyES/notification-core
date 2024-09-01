package com.es.notificationcore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.es.notificationcore.data.model.NotificationEntity

@Database(entities = [NotificationEntity::class], version = 1, exportSchema = false)
abstract class NotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}
