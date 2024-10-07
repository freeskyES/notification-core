package com.es.notificationcore.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.es.notificationcore.data.noti.local.entity.NotiEntity

@Dao
interface NotiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotiEntity)

    @Query("SELECT * FROM notifications ORDER BY notiAt DESC")
    suspend fun getAllNotifications(): List<NotiEntity>
}
