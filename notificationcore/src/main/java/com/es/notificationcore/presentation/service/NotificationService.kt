package com.es.notificationcore.presentation.service

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.domain.usecase.ParseNotiUseCase
import com.es.notificationcore.domain.usecase.SaveNotiUseCase
import com.es.notificationcore.presentation.service.worker.NotificationWorker
import com.es.notificationcore.utils.GsonUtil
import com.es.notificationcore.utils.NotificationHelper
import com.es.notificationcore.utils.NotificationHelper.Companion.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : NotificationListenerService() {
    @Inject
    lateinit var parseNotiUseCase: ParseNotiUseCase

    @Inject
    lateinit var saveNotiUseCase: SaveNotiUseCase

    @Inject
    lateinit var notificationHelper: NotificationHelper

    private val serviceScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.i("NotificationService started")
        notificationHelper = NotificationHelper(this)
        // 알림 채널 생성
        notificationHelper.createNotificationChannel()

        startForegroundService()
    }

    private fun startForegroundService() {
        try {
            val notification: Notification =
                notificationHelper.createNotification(
                    createServiceIntent(this),
                )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForeground(
                    NOTIFICATION_ID,
                    notification,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
                )
            } else {
                startForeground(NOTIFICATION_ID, notification)
            }
        } catch (e: Exception) {
            Timber.e("Error starting foreground service: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        Timber.i("onNotificationPosted")
        sbn ?: return

        serviceScope.launch {
            val noti = parseNotiUseCase.execute(sbn)

            noti?.let {
                Timber.i("onNotificationPosted notificationData : $it")

                // process Noti in WorkManger
                sendToWorkManager(it)
            }
        }
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        Timber.i("onStartCommand startId $startId")
        return START_STICKY
    }

    private fun sendToWorkManager(noti: Noti) {
        val notiJson = GsonUtil.toJson(noti)

        val inputData = Data.Builder()
            .putString("noti_data", notiJson)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    companion object {
        fun createServiceIntent(context: Context): Intent =
            Intent(context, NotificationService::class.java)
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Timber.i("NotificationService connected")
    }

    override fun onListenerDisconnected() {
        Timber.i("NotificationService disconnected")
        super.onListenerDisconnected()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        Timber.i("NotificationService destroyed")
    }
}
