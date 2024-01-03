package com.cious.learnhub.data.network.api.datasource

import androidx.datastore.dataStore
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.network.api.model.login.LoginResponse
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.data.network.api.model.otp.OtpResponse
import com.cious.learnhub.data.network.api.model.register.RegisterRequest
import com.cious.learnhub.data.network.api.model.register.RegisterResponse
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordRequest
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordResponse
import com.cious.learnhub.data.network.api.service.AuthenticationService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AuthDataSourceImplTest {

    @MockK
    lateinit var service: AuthenticationService

    private lateinit var dataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = AuthDataSourceImpl(service)
    }

    @Test
    fun sendOtpRegister() {
        runTest {
            val mockResponse = mockk<OtpResponse>(relaxed = true)
            coEvery { service.otpRegister(any()) } returns mockResponse
            val mockRequest = mockk<OtpRequest>(relaxed = true)
            val response = dataSource.sendOtpRegister(mockRequest)
            coVerify { service.otpRegister(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val mockResponse = mockk<RegisterResponse>(relaxed = true)
            coEvery { service.register(any()) } returns mockResponse
            val mockRequest = mockk<RegisterRequest>(relaxed = true)
            val response = dataSource.doRegister(mockRequest)
            coVerify { service.register(any()) }
            assertEquals(response, mockResponse)

        }
    }

    @Test
    fun doLogin() {
        runTest {
            val mockResponse = mockk<LoginResponse>(relaxed = true)
            coEvery { service.login(any()) } returns mockResponse
            val mockRequest = mockk<LoginRequest>(relaxed = true)
            val response = dataSource.doLogin(mockRequest)
            coVerify { service.login(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun sendOtpResetPassword() {
        runTest {
            val mockResponse = mockk<OtpResponse>(relaxed = true)
            val mockRequest = mockk<OtpRequest>(relaxed = true)
            coEvery { service.otpResetPassword(any()) } returns mockResponse
            val response = dataSource.sendOtpResetPassword(mockRequest)
            coVerify { service.otpResetPassword(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun doResetPassword() {
        runTest {
            val mockResponse = mockk<VerifyResetPasswordResponse>(relaxed = true)
            val mockRequest = mockk<VerifyResetPasswordRequest>(relaxed = true)
            coEvery { service.resetPassword(any()) } returns mockResponse
            val response = dataSource.doResetPassword(mockRequest)
            coVerify { service.resetPassword(any()) }
            assertEquals(response, mockResponse)
        }
    }
}