package com.es.notificationdemo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.es.notificationcore.presentation.initializer.NotificationInitializer
import com.es.notificationdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val notificationViewModel: NotificationViewModel by viewModels()

    @Inject
    lateinit var notificationInitializer: NotificationInitializer

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        notificationViewModel.setNotificationInitializer(notificationInitializer)

        notificationViewModel.notifications.observe(
            this,
            Observer { notifications ->
                binding.textView.text =
                    notifications.joinToString("\n\n") {
                        "Title: ${it.title}, Content: ${it.content}, Timestamp: ${it.timestamp}"
                    }
            },
        )

        binding.fetchButton.setOnClickListener {
            notificationViewModel.fetchNotifications()
        }
    }
}
