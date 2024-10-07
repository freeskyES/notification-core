package com.es.notificationcore.presentation.initializer

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.domain.usecase.GetNotiUseCase
import com.es.notificationcore.presentation.service.NotificationService
import com.es.notificationcore.utils.PermissionHandler
import javax.inject.Inject

class NotificationInitializerImpl @Inject constructor(
    private val activity: AppCompatActivity,
    private val permissionHandler: PermissionHandler,
    private val getNotiUseCase: GetNotiUseCase
) : NotificationInitializer {

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

    override suspend fun getNotifications(): List<Noti> {
        return getNotiUseCase.execute()
    }
}
