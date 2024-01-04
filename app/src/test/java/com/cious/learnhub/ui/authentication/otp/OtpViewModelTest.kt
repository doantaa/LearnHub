package com.cious.learnhub.ui.authentication.otp

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.model.RegisterData
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.SessionManager
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

class OtpViewModelTest {

    @MockK
    private lateinit var extras: Bundle

    @MockK
    private lateinit var repository: AuthRepository

    @MockK
    private lateinit var sessionManager: SessionManager

    @get: Rule
    val testRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule: TestRule = MainCoroutineRule()

    private lateinit var viewModel: OtpViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        extras = mockk(relaxed = true)

        val responseData = mockk<AuthenticationData>(relaxed = true)
        coEvery { extras.getParcelable<AuthenticationData>("USER_REGISTER_DATA") } returns responseData

        coEvery { repository.isLogin() } returns false

        coEvery { sessionManager.saveAuthToken("123456") } returns Unit

        val mockRegisterData = mockk<RegisterData>(relaxed = true)
        coEvery { repository.doRegister(any(), any()) } returns flow {
            emit(ResultWrapper.Success(mockRegisterData))
        }

        viewModel = spyk(OtpViewModel(extras, repository, sessionManager))
    }

    @Test
    fun `get dataParcel`() {
        val result = viewModel.dataParcel
        println(result)
        assertTrue(result is AuthenticationData)
    }

    @Test
    fun isLogin() {
        coVerify { repository.isLogin() }
    }

    @Test
    fun saveAuthToken() {
        viewModel.saveAuthToken("123456")
        coVerify { sessionManager.saveAuthToken("123456") }
    }

    @Test
    fun doRegister(){
        val mockRequest = mockk<AuthenticationData>(relaxed = true)
        viewModel.doRegister(mockRequest, "123456")
        coVerify { repository.doRegister(any(), any()) }
    }
}