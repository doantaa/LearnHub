package com.cious.learnhub.data.network.api.model.notification

import org.junit.Assert.assertEquals
import org.junit.Test

class MarkAsReadNotificationResponseTest {

    @Test
    fun testToMarkAsReadNotification() {
        val markAsReadNotificationResponse = MarkAsReadNotificationResponse("Test message")

        val result = markAsReadNotificationResponse.toMarkAsReadNotification()

        assertEquals("Test message", result.message)
    }

    @Test
    fun testToMarkAsReadNotificationWithNullMessage() {

        val markAsReadNotificationResponse = MarkAsReadNotificationResponse(null)

        val result = markAsReadNotificationResponse.toMarkAsReadNotification()

        assertEquals("", result.message)
    }
}
