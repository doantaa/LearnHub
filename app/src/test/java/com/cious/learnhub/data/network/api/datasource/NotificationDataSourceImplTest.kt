package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.notification.MarkAsReadNotificationResponse
import com.cious.learnhub.data.network.api.model.notification.NotificationItemResponse
import com.cious.learnhub.data.network.api.model.notification.NotificationResponse
import com.cious.learnhub.data.network.api.service.NotificationService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NotificationDataSourceImplTest {
    @MockK
    private lateinit var service: NotificationService

    lateinit var dataSource: NotificaitonDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = NotificationDataSourceImpl(service)
    }

    @Test
    fun getNotification() {
        val mockNotificationResponse = mockk<NotificationItemResponse>()
        val mockResponse =
            NotificationResponse(data = listOf(mockNotificationResponse), "success", true)
        coEvery { service.getNotification() } returns mockResponse

        runTest {
            val response = dataSource.getNotification()
            coVerify { service.getNotification() }
            Assert.assertEquals(response, mockResponse)
        }
    }

    @Test
    fun markAsReadNotification() {
        val mockResponse = mockk<MarkAsReadNotificationResponse>(relaxed = true)
        coEvery { service.markAsReadNotification(any()) } returns  mockResponse
        runTest{
            val response = dataSource.markAsReadNotification(1)
            coVerify { service.markAsReadNotification(any()) }
            Assert.assertEquals(response, mockResponse)
        }
    }
}