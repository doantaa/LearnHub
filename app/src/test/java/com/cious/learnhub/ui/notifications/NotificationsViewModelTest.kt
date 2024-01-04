package com.cious.learnhub.ui.notifications

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.data.repository.NotifiacationRepository
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.tools.getOrAwaitValue
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class NotificationsViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())


    @MockK
    private lateinit var notificationRepository: NotifiacationRepository

    @MockK
    private lateinit var authRepository: AuthRepository

    lateinit var viewModel: NotificationsViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { authRepository.isLogin() } returns true
        coEvery { notificationRepository.getNotification() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }
        viewModel = spyk(
            NotificationsViewModel(notificationRepository, authRepository)
        )
    }

    @Test
    fun `get notification live data`(){
        viewModel.getNotification()
        val result = viewModel.notifRequestResult.getOrAwaitValue()
        Assert.assertTrue(result.payload is List<NotificationModel>)
        coVerify { notificationRepository.getNotification() }
    }

    @Test
    fun `send mark as read notification`(){
        viewModel.markAsReadNotification(2)
    }

    @Test
    fun `check user login`(){
        val result = viewModel.isLogin
        Assert.assertEquals(result, true)
        coVerify { authRepository.isLogin() }
    }
}