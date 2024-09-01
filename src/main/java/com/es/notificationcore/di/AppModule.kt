package com.es.notificationcore.di

import android.content.Context
import androidx.room.Room
import com.es.notificationcore.data.local.NotificationDao
import com.es.notificationcore.data.local.NotificationDatabase
import com.es.notificationcore.data.repository.NotificationRepositoryImpl
import com.es.notificationcore.domain.repository.NotificationRepository
import com.es.notificationcore.domain.usecase.ProcessNotificationUseCase
import com.es.notificationcore.utils.NotificationHelper
import com.es.notificationcore.utils.NotificationParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): NotificationDatabase =
        Room
            .databaseBuilder(
                appContext,
                NotificationDatabase::class.java,
                "notification_database",
            ).build()

    @Provides
    fun provideNotificationDao(db: NotificationDatabase): NotificationDao = db.notificationDao()

    @Provides
    @Singleton
    fun provideNotificationRepository(notificationDao: NotificationDao): NotificationRepository =
        NotificationRepositoryImpl(notificationDao)

    @Provides
    @Singleton
    fun provideProcessNotificationUseCase(repository: NotificationRepository): ProcessNotificationUseCase =
        ProcessNotificationUseCase(repository)

    @Provides
    @Singleton
    fun provideNotificationParser(): NotificationParser = NotificationParser()

    @Provides
    @Singleton
    fun provideNotificationHelper(
        @ApplicationContext appContext: Context,
    ): NotificationHelper = NotificationHelper(appContext)
}
