package com.cious.learnhub.ui.notifications.notificationdetail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class NotificationDetailViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var mockBundle: Bundle

    lateinit var viewModel: NotificationDetailViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        val mockNotificationBundle = mockk<NotificationModel>()
        coEvery { mockBundle.getParcelable<NotificationModel>("EXTRA_NOTIFICATION") } returns mockNotificationBundle
        viewModel = spyk(
            NotificationDetailViewModel(mockBundle)
        )

    }

    @Test
    fun getNotification() {
        val result = viewModel.notification
        Assert.assertTrue(result is NotificationModel)
    }
}