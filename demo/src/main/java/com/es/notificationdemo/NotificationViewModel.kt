package com.es.notificationdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.presentation.initializer.NotificationInitializer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel
@Inject
constructor() : ViewModel() {
    private lateinit var notificationInitializer: NotificationInitializer

    private val _notifications = MutableLiveData<List<Noti>>()
    val notifications: LiveData<List<Noti>> = _notifications

    fun fetchNotifications() {
        viewModelScope.launch {
            try {
                val notifications = notificationInitializer.getNotifications()
                _notifications.postValue(notifications)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setNotificationInitializer(notificationInitializer: NotificationInitializer) {
        this.notificationInitializer = notificationInitializer
        initializeService()
    }

    private fun initializeService() {
        // 서비스 초기화 및 시작
        notificationInitializer.initializeAndStartService()
    }
}
