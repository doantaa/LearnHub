package com.cious.learnhub.data.network.api.model.notification

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class NotificationResponseTest {

    @Test
    fun testToNotificationData() {

        val mockNotificationItemResponse = mock(NotificationItemResponse::class.java)
        val mockNotificationResponse = NotificationResponse(
            data = listOf(mockNotificationItemResponse),
            message = "Test message",
            success = true
        )

        val result = mockNotificationResponse.toNotificationData()

        assertEquals("Test message", result.message)
    }

    @Test
    fun testToNotificationDataWithNullMessage() {

        val mockNotificationItemResponse = mock(NotificationItemResponse::class.java)
        val mockNotificationResponse = NotificationResponse(
            data = listOf(mockNotificationItemResponse),
            message = null,
            success = true
        )

        val result = mockNotificationResponse.toNotificationData()

        assertEquals("", result.message)
    }

    @Test
    fun testToNotificationDataWithNullData() {

        val mockNotificationResponse = NotificationResponse(
            data = null,
            message = "Test message",
            success = true
        )

        val result = mockNotificationResponse.toNotificationData()

        assertEquals("Test message", result.message)
    }

    @Test
    fun testToNotificationDataWithEmptyData() {

        val mockNotificationResponse = NotificationResponse(
            data = emptyList(),
            message = "Test message",
            success = true
        )

        val result = mockNotificationResponse.toNotificationData()

        assertEquals("Test message", result.message)
    }
}
