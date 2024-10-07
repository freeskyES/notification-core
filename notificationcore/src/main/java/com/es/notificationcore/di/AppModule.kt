package com.es.notificationcore.di

import android.content.Context
import androidx.room.Room
import com.es.notificationcore.data.local.NotiDao
import com.es.notificationcore.data.local.NotiDatabase
import com.es.notificationcore.data.noti.DefaultNotiRepository
import com.es.notificationcore.data.noti.NotiRepository
import com.es.notificationcore.data.noti.service.DefaultNotiService
import com.es.notificationcore.data.noti.service.NotiService
import com.es.notificationcore.data.noti.service.chat.ChatServiceFactory
import com.es.notificationcore.data.noti.service.chat.WhatsAppChatService
import com.es.notificationcore.domain.usecase.ParseNotiUseCase
import com.es.notificationcore.domain.usecase.SaveNotiUseCase
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
    ): NotiDatabase =
        Room
            .databaseBuilder(
                appContext,
                NotiDatabase::class.java,
                "notification_database",
            ).build()

    @Provides
    fun provideNotificationDao(db: NotiDatabase): NotiDao = db.notificationDao()

    @Provides
    @Singleton
    fun provideNotificationRepository(notiDao: NotiDao): NotiRepository =
        DefaultNotiRepository(notiDao)

    @Provides
    @Singleton
    fun provideNotiService(notificationParser: NotificationParser): NotiService {
        return DefaultNotiService(notificationParser)
    }

    @Provides
    @Singleton
    fun provideChatServiceFactory(): ChatServiceFactory {
        return ChatServiceFactory()
    }

    @Provides
    @Singleton
    fun provideWhatsAppChatService(): WhatsAppChatService {
        return WhatsAppChatService()
    }

    @Provides
    @Singleton
    fun provideParseNotiUseCase(
        notiService: NotiService,
        chatServiceFactory: ChatServiceFactory
    ): ParseNotiUseCase {
        return ParseNotiUseCase(notiService, chatServiceFactory)
    }

    @Provides
    @Singleton
    fun provideSaveNotiUseCase(
        repository: NotiRepository
    ): SaveNotiUseCase {
        return SaveNotiUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideNotificationParser(): NotificationParser = NotificationParser()

    @Provides
    @Singleton
    fun provideNotificationHelper(
        @ApplicationContext appContext: Context,
    ): NotificationHelper = NotificationHelper(appContext)
}
