package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.notification.MarkAsReadNotificationResponse
import com.cious.learnhub.data.network.api.model.notification.MarkAsReadNotificationResponseTest
import com.cious.learnhub.data.network.api.model.notification.NotificationResponse
import com.cious.learnhub.data.network.api.model.notification.NotificationResponseTest
import com.cious.learnhub.data.network.api.service.NotificationService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NotificationDataSourceImplTest {

    @Mock
    private lateinit var mockService: NotificationService

    private lateinit var notificationDataSource: NotificaitonDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        notificationDataSource = NotificationDataSourceImpl(mockService)
    }

    @Test
    fun testGetNotification() {
        runBlocking {

            val mockResponse = NotificationResponse(NotificationResponseTest)
            `when`(mockService.getNotification()).thenReturn(mockResponse)

            val result = notificationDataSource.getNotification()

            assertEquals(mockResponse, result)
        }
    }

    @Test
    fun testMarkAsReadNotification() {
        runBlocking {
            val notificationId = 123

            // Mock response
            val mockResponse = MarkAsReadNotificationResponse(MarkAsReadNotificationResponseTest)
            `when`(mockService.markAsReadNotification(notificationId)).thenReturn(mockResponse)

            val result = notificationDataSource.markAsReadNotification(notificationId)

            assertEquals(mockResponse, result)
        }
    }
}
