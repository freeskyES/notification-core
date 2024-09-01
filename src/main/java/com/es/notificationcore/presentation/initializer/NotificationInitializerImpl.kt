package com.es.notificationcore.presentation.initializer

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.es.notificationcore.presentation.service.NotificationService
import com.es.notificationcore.utils.PermissionHandler

class NotificationInitializerImpl(
    private val activity: AppCompatActivity,
) : NotificationInitializer {
    private val permissionHandler: PermissionHandler = PermissionHandler(activity)

    override fun initializeAndStartService() {
        // 권한 처리 및 서비스 시작
        permissionHandler.checkAndRequestPermissions {
            // system 에서 활성화 처리
        }
    }

    override fun startService() {
        val serviceIntent = NotificationService.createServiceIntent(activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(serviceIntent)
        } else {
            activity.startService(serviceIntent)
        }
    }
}
