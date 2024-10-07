package com.es.notificationcore.presentation.service

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.domain.usecase.ParseNotiUseCase
import com.es.notificationcore.domain.usecase.SaveNotiUseCase
import com.es.notificationcore.utils.NotificationHelper
import com.es.notificationcore.utils.NotificationHelper.Companion.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : NotificationListenerService() {
    @Inject
    lateinit var parseNotiUseCase: ParseNotiUseCase

    @Inject
    lateinit var saveNotiUseCase: SaveNotiUseCase

    @Inject
    lateinit var notificationHelper: NotificationHelper

    private val notificationQueue = LinkedBlockingQueue<Noti>()
    private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    override fun onCreate() {
        super.onCreate()
        Timber.i("NotificationService started")
        notificationHelper = NotificationHelper(this)
        // 알림 채널 생성
        notificationHelper.createNotificationChannel()

        startForegroundService()

        // 알림 처리 in background
        executor.execute {
            while (true) {
                try {
                    val notificationData = notificationQueue.take() // 큐에서 대기 중인 작업을 가져옴
                    processNotification(notificationData)
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    break
                }
            }
        }
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
            e.printStackTrace()
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        Timber.i("onNotificationPosted")
        sbn ?: return

        val notificationData = parseNotiUseCase.execute(sbn)
        notificationData?.let {
            Timber.i("onNotificationPosted notificationData : $it")
            notificationQueue.offer(it) // 큐에 작업을 추가
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

    private fun processNotification(notificationData: Noti) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // 알림을 처리
                saveNotiUseCase.execute(notificationData)
            } catch (e: Exception) {
                Timber.e("Failed to process notification $e")
            }
        }
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
        Timber.i("NotificationService destroyed")
    }
}
