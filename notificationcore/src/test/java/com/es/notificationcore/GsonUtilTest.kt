package com.es.notificationcore

import com.es.notificationcore.data.noti.Noti
import com.es.notificationcore.data.noti.vo.NotiBase
import com.es.notificationcore.utils.GsonUtil
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock

class GsonUtilTest {

    val mockNotiBase: NotiBase = mock(NotiBase::class.java)
    val noti = Noti(id = "id", appId = "appId", base = mockNotiBase)

    @Test
    fun `should serialize and deserialize Noti object correctly`() {
        val notiJson = GsonUtil.toJson(noti)
        val deserializedNoti = GsonUtil.fromJson(notiJson, Noti::class.java)

        assertNotNull(deserializedNoti)
        assertEquals(noti.id, deserializedNoti.id)
    }
}