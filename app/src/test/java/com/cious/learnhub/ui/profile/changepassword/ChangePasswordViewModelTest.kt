package com.cious.learnhub.ui.profile.changepassword

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordRequest
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.model.ChangePasswordModel
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

class ChangePasswordViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var repository: ProfileRepository
    lateinit var viewModel: ChangePasswordViewModel

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        val mockChangePasswordResponse = mockk<ChangePasswordModel>()
        coEvery { repository.doChangePassword(any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    mockChangePasswordResponse
                )
            )
        }

        viewModel = spyk(
            ChangePasswordViewModel(repository)
        )
    }

    @Test
    fun`get change password result live data`(){
        val mockRequest = mockk<ChangePasswordRequest>(relaxed = true)
        viewModel.doChangePassword(mockRequest)
        val result = viewModel.changePasswordRequestResult.getOrAwaitValue {  }
        Assert.assertTrue(result.payload is ChangePasswordModel)
        coVerify { repository.doChangePassword(any()) }
    }
}