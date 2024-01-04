package com.cious.learnhub.ui.authentication.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.model.OtpData
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flow

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {

    @MockK
    private lateinit var repository: AuthRepository

    @get: Rule
    val testRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule: TestRule = MainCoroutineRule()

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(RegisterViewModel(repository))
        val mockOtpData = mockk<OtpData>(relaxed = true)
        coEvery { repository.sendOtpRegister(any()) } returns flow {
            emit(ResultWrapper.Success(mockOtpData))
        }
    }

    @Test
    fun sendOtpRegister() {
        val mockRequest = mockk<OtpRequest>(relaxed = true)
        viewModel.sendOtpRegister(mockRequest)
        coVerify { repository.sendOtpRegister(any()) }
    }
}