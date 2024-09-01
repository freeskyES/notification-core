package com.es.notificationcore.utils

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import timber.log.Timber

class PermissionHandler(
    private val activity: AppCompatActivity,
) {
    private var onPermissionsGranted: (() -> Unit)? = null

    fun checkAndRequestPermissions(onPermissionsGranted: () -> Unit) {
        this.onPermissionsGranted = onPermissionsGranted

        when {
            checkPostNotification() -> requestPostNotification()
            !isNotificationAccessEnabled() -> requestNotificationAccess()
            else -> onPermissionsGranted()
        }
    }

    private fun requestNotificationAccess() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        activityLauncherForNotificationAccess.launch(intent)
    }

    private val activityLauncherForNotificationAccess =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (isNotificationAccessEnabled()) {
                Timber.i("Notification access is enabled.")
                onPermissionsGranted?.invoke()
            } else {
                Timber.i("Notification access is not enabled.")
                // 알림 액세스 권한이 거부되었을 때 처리
            }
        }

    private val activityLauncherForPostNotification =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                Timber.i("Post Notification is enabled.")
                checkRemainingPermissions()
            } else {
                Timber.i("Post Notification is not enabled.")
                // 포스트 알림 권한이 거부되었을 때 처리
                checkRemainingPermissions()
            }
        }

    private fun checkRemainingPermissions() {
        if (!isNotificationAccessEnabled()) {
            requestNotificationAccess()
        } else {
            onPermissionsGranted?.invoke()
        }
    }

    private fun checkPostNotification(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS,
            ) !=
            PackageManager.PERMISSION_GRANTED
                .also { Timber.i("postNotification : $it") }

    private fun requestPostNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activityLauncherForPostNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun isNotificationAccessEnabled(): Boolean {
        val pkgName = activity.packageName
        val flat = Settings.Secure.getString(activity.contentResolver, "enabled_notification_listeners")
        return flat?.split(":")?.map { ComponentName.unflattenFromString(it) }?.any { it?.packageName == pkgName } ?: false
    }
}
