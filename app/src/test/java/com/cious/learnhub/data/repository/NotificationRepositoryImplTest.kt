package com.cious.learnhub.data.repository

import app.cash.turbine.test
import com.cious.learnhub.data.network.api.datasource.NotificaitonDataSource
import com.cious.learnhub.data.network.api.model.notification.MarkAsReadNotificationResponse
import com.cious.learnhub.data.network.api.model.notification.NotificationItemResponse
import com.cious.learnhub.data.network.api.model.notification.NotificationResponse
import com.cious.learnhub.utils.ApiException
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class NotificationRepositoryImplTest {

    @MockK
    private lateinit var dataSource: NotificaitonDataSource
    lateinit var repository: NotifiacationRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = NotificationRepositoryImpl(dataSource)
    }

    @Test
    fun `get notification, result loading`() {
        val mockNotification = mockk<NotificationResponse>(relaxed = true)
        coEvery { dataSource.getNotification() } returns mockNotification

        runTest {
            repository.getNotification().map {
                delay(100)
                it
            }.test {
                delay(101)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getNotification() }
            }
        }
    }


    @Test
    fun `get notification, result success`() {
        val mockNotificationItem = mockk<NotificationItemResponse>(relaxed = true)
        val fakeNotification = NotificationResponse(listOf(mockNotificationItem), "success", true)
        coEvery { dataSource.getNotification() } returns fakeNotification

        runTest {
            repository.getNotification().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data)
                Assert.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getNotification() }
            }
        }
    }


    @Test
    fun `get notification, result empty`() {
        val fakeNotification = NotificationResponse(emptyList(), "success", true)
        coEvery { dataSource.getNotification() } returns fakeNotification

        runTest {
            repository.getNotification().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data)
                Assert.assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getNotification() }
            }
        }
    }

    @Test
    fun `get notification, result error IO Exception`() {
        coEvery { dataSource.getNotification() } throws IOException()

        runTest {
            repository.getNotification().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data.exception)
                Assert.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getNotification() }
            }
        }
    }


    @Test
    fun `get notification, result error Api Exception`() {
        coEvery { dataSource.getNotification() } throws ApiException(null, httpCode = 400, null)

        runTest {
            repository.getNotification().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getNotification() }
            }
        }
    }
    @Test
    fun `mark as read notification, result loading`() {
        val mockResponse = mockk<MarkAsReadNotificationResponse>(relaxed = true)
        coEvery { dataSource.markAsReadNotification(any()) } returns mockResponse

        runTest {
            repository.markAsReadNotification(1).map {
                delay(100)
                it
            }.test {
                delay(101)
                val data = expectMostRecentItem()
                coVerify { dataSource.markAsReadNotification(any()) }
                Assert.assertTrue(data is ResultWrapper.Loading)
            }
        }
    }
    @Test
    fun `mark as read notification, result success`(){
        val mockResponse = mockk<MarkAsReadNotificationResponse>(relaxed = true)
        coEvery { dataSource.markAsReadNotification(any()) } returns mockResponse

        runTest {
            repository.markAsReadNotification(1).map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                coVerify { dataSource.markAsReadNotification(any()) }
                Assert.assertTrue(data is ResultWrapper.Success)
            }
        }
    }

}