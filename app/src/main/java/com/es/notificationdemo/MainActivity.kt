package com.es.notificationdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.es.notificationcore.presentation.initializer.NotificationInitializer
import com.es.notificationcore.presentation.initializer.NotificationInitializerImpl

class MainActivity : AppCompatActivity() {
    private lateinit var notificationInitializer: NotificationInitializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 알림 액세스 권한 확인 및 요청
        setupNotificationCore()
    }

    private fun setupNotificationCore() {
        notificationInitializer = NotificationInitializerImpl(this)
        notificationInitializer.initializeAndStartService()
    }
}