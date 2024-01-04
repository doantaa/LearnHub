package com.cious.learnhub.ui.authentication.resetpassword

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordRequest
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.model.UserOtpPasswordData
import com.cious.learnhub.model.UserResetData
import com.cious.learnhub.model.VerifyResetPasswordData
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class VerifyResetPasswordViewModelTest {

    @MockK
    private lateinit var extras: Bundle

    @MockK
    private lateinit var repository: AuthRepository

    @get: Rule
    val testRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule: TestRule = MainCoroutineRule()

    private lateinit var viewModel: VerifyResetPasswordViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        extras = mockk(relaxed = true)

        val responseData = mockk<UserOtpPasswordData>()
        coEvery { extras.getParcelable<UserOtpPasswordData>("USER_OTP_PASSWORD_DATA") } returns responseData


        val mockVerifyResetPasswordData = mockk<VerifyResetPasswordData>(relaxed = true)
        coEvery { repository.doResetPassword(any()) } returns flow {
            emit(ResultWrapper.Success(mockVerifyResetPasswordData))
        }

        viewModel = spyk(VerifyResetPasswordViewModel(extras, repository))
    }

    @Test
    fun `get dataParcel`() {
        val result = viewModel.dataParcel
        assertTrue(result is UserOtpPasswordData)
    }

    @Test
    fun doResetPassword() {
        val mockRequest = mockk<VerifyResetPasswordRequest>(relaxed = true)
        viewModel.doResetPassword(mockRequest)
        coVerify { repository.doResetPassword(any()) }
    }
}