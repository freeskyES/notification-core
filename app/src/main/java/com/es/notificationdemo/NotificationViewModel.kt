package com.es.notificationdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.es.notificationcore.domain.model.NotificationData
import com.es.notificationcore.presentation.initializer.NotificationInitializer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {

    private lateinit var notificationInitializer: NotificationInitializer

    private val _notifications = MutableLiveData<List<NotificationData>>()
    val notifications: LiveData<List<NotificationData>> = _notifications

    fun fetchNotifications() {
        viewModelScope.launch {
            val notifications = notificationInitializer.getNotifications()
            _notifications.postValue(notifications)
        }
    }

    fun setNotificationInitializer(notificationInitializer: NotificationInitializer) {
        this.notificationInitializer = notificationInitializer
    }
}