package com.es.notificationcore.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.es.notificationcore.domain.usecase.GetNotiUseCase
import com.es.notificationcore.presentation.initializer.NotificationInitializer
import com.es.notificationcore.presentation.initializer.NotificationInitializerImpl
import com.es.notificationcore.utils.PermissionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
object initializerModule {

    @Provides
    @ActivityScoped
    fun provideNotificationInitializer(
        @ActivityContext activity: Context,
        permissionHandler: PermissionHandler,
        getNotiUseCase: GetNotiUseCase
    ): NotificationInitializer {
        return NotificationInitializerImpl(
            activity as AppCompatActivity,
            permissionHandler,
            getNotiUseCase
        )
    }

    @Provides
    @ActivityScoped
    fun providePermissionHandler(
        @ActivityContext activity: Context
    ): PermissionHandler {
        return PermissionHandler(activity as AppCompatActivity)
    }
}