package com.cious.learnhub.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.BuildConfig
import com.cious.learnhub.data.network.api.model.notification.MarkAsReadNotificationResponse
import com.cious.learnhub.data.network.api.model.notification.NotificationResponse
import com.cious.learnhub.utils.AuthInterceptor
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationServiceTest {

    @Mock
    private lateinit var mockChuckerInterceptor: ChuckerInterceptor

    @Mock
    private lateinit var mockAuthInterceptor: AuthInterceptor

    @Mock
    private lateinit var mockNotificationService: NotificationService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(
            NotificationService.invoke(mockChuckerInterceptor, mockAuthInterceptor)
        ).thenReturn(mockNotificationService)
    }

    @Test
    fun testGetNotification() {
        runBlocking {

            val mockResponse = NotificationResponse(/* mock your response data */)
            `when`(mockNotificationService.getNotification()).thenReturn(mockResponse)

            val result = mockNotificationService.getNotification()

            assertEquals(mockResponse, result)
        }
    }

    @Test
    fun testMarkAsReadNotification() {
        runBlocking {
            val notificationId = 123

            val mockResponse = MarkAsReadNotificationResponse(/* mock your response data */)
            `when`(mockNotificationService.markAsReadNotification(notificationId)).thenReturn(mockResponse)

            val result = mockNotificationService.markAsReadNotification(notificationId)

            assertEquals(mockResponse, result)
        }
    }
}
