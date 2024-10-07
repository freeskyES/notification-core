package com.es.notificationcore.domain.usecase

import android.service.notification.StatusBarNotification
import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.data.noti.service.NotiService
import com.es.notificationcore.data.noti.service.chat.ChatServiceFactory
import com.es.notificationcore.data.noti.vo.NotiBase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ParseNotiUseCaseTest {

    private val notiService: NotiService = mock()
    private val chatServiceFactory: ChatServiceFactory = mock()
    private val parseNotiUseCase = ParseNotiUseCase(notiService, chatServiceFactory)

    @Test
    fun `test notification parsing`() = runBlocking {
        val sbn: StatusBarNotification = mock()

        whenever(notiService.canParsing(sbn)).thenReturn(true)
        whenever(notiService.parseNoti(sbn)).thenReturn(mockNoti())

        val result = parseNotiUseCase.execute(sbn)
        assertEquals(result, mockNoti())
    }

    @Test
    fun `test self notification parsing`() = runBlocking {
        val sbn: StatusBarNotification = mock()

        whenever(notiService.canParsing(sbn)).thenReturn(false)

        val result = parseNotiUseCase.execute(sbn)
        assertNull(result)
    }

    private fun mockNoti(): Noti {
        return Noti(
            id = "1",
            appId = "com.example",
            base = NotiBase("Title", "SubTitle", "Content", 123L)
        )
    }
}