package com.cious.learnhub.data.repository

import app.cash.turbine.test
import com.cious.learnhub.data.network.api.datasource.AuthDataSource
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.network.api.model.login.LoginResponse
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.data.network.api.model.otp.OtpResponse
import com.cious.learnhub.data.network.api.model.register.RegisterResponse
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordRequest
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordResponse
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.SessionManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {

    @MockK
    lateinit var remoteDataSource:  AuthDataSource
    @MockK
    lateinit var remoteSessionManager: SessionManager

    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = AuthRepositoryImpl(remoteDataSource, remoteSessionManager)
    }

    @Test
    fun `sendOtpRegister, with result loading`() {
        val mockOtpResponse = mockk<OtpResponse>(relaxed = true)
        val mockOtpRequest = mockk<OtpRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.sendOtpRegister(any()) } returns mockOtpResponse
            repository.sendOtpRegister(mockOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.sendOtpRegister(any()) }
            }
        }
    }

    @Test
    fun `sendOtpRegister, with result success`() {
        val mockOtpResponse = mockk<OtpResponse>(relaxed = true)
        val mockOtpRequest = mockk<OtpRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.sendOtpRegister(any()) } returns mockOtpResponse
            repository.sendOtpRegister(mockOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { remoteDataSource.sendOtpRegister(any()) }
            }
        }
    }

    @Test
    fun `sendOtpRegister, with result Error`() {
        val mockOtpRequest = mockk<OtpRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.sendOtpRegister(any()) } throws IllegalStateException("Mock error")
            repository.sendOtpRegister(mockOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.sendOtpRegister(any()) }
            }
        }
    }

    @Test
    fun `doRegister, with result loading`() {
        val mockRegisterResponse = mockk<RegisterResponse>(relaxed = true)
        val mockAuthData = mockk<AuthenticationData>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.doRegister(any()) } returns mockRegisterResponse
            repository.doRegister(mockAuthData, "123456").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.doRegister(any()) }
            }
        }
    }

    @Test
    fun `doRegister, with result success`() {
        val mockRegisterResponse = mockk<RegisterResponse>(relaxed = true)
        val mockAuthData = mockk<AuthenticationData>(relaxed = true)
        runTest {
            coEvery { remoteSessionManager.saveAuthToken(any()) } returns Unit
            coEvery { remoteDataSource.doRegister(any()) } returns mockRegisterResponse
            repository.doRegister(mockAuthData, "123456").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { remoteDataSource.doRegister(any()) }
            }
        }
    }

    @Test
    fun `doRegister, with result Error`() {
        val mockAuthData = mockk<AuthenticationData>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.doRegister(any()) } throws IllegalStateException("Mock error")
            repository.doRegister(mockAuthData, "123456").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.doRegister(any()) }
            }
        }
    }

    @Test
    fun `doLogin, with result loading`() {
        val mockLoginResponse = mockk<LoginResponse>(relaxed = true)
        val mockLoginRequest = mockk<LoginRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.doLogin(any()) } returns mockLoginResponse
            repository.doLogin(mockLoginRequest).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.doLogin(any()) }
            }
        }
    }

    @Test
    fun `doLogin, with result success`() {
        val mockLoginResponse = mockk<LoginResponse>(relaxed = true)
        val mockLoginRequest = mockk<LoginRequest>(relaxed = true)
        runTest {
            coEvery { remoteSessionManager.saveAuthToken(any()) } returns Unit
            coEvery { remoteDataSource.doLogin(any()) } returns mockLoginResponse
            repository.doLogin(mockLoginRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                println(data.exception?.message)
                assertTrue(data is ResultWrapper.Success)
                coVerify { remoteDataSource.doLogin(any()) }
            }
        }
    }

    @Test
    fun `doLogin, with result Error`() {
        val mockLoginRequest = mockk<LoginRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.doLogin(any()) } throws IllegalStateException("Mock error")
            repository.doLogin(mockLoginRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.doLogin(any()) }
            }
        }
    }

    @Test
    fun `sendOtpPassword, with result loading`() {
        val mockOtpResponse = mockk<OtpResponse>(relaxed = true)
        val mockOtpRequest = mockk<OtpRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.sendOtpResetPassword(any()) } returns mockOtpResponse
            repository.sendOtpPassword(mockOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.sendOtpResetPassword(any()) }
            }
        }
    }

    @Test
    fun `sendOtpPassword, with result success`() {
        val mockOtpResponse = mockk<OtpResponse>(relaxed = true)
        val mockOtpRequest = mockk<OtpRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.sendOtpResetPassword(any()) } returns mockOtpResponse
            repository.sendOtpPassword(mockOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { remoteDataSource.sendOtpResetPassword(any()) }
            }
        }
    }

    @Test
    fun `sendOtpPassword, with result Error`() {
        val mockOtpRequest = mockk<OtpRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.sendOtpResetPassword(any()) } throws IllegalStateException("Mock error")
            repository.sendOtpPassword(mockOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.sendOtpResetPassword(any()) }
            }
        }
    }

    @Test
    fun `doRegisterPassword, with result loading`() {
        val mockVerifyResetPasswordResponse = mockk<VerifyResetPasswordResponse>(relaxed = true)
        val mockVerifyResetPasswordRequest = mockk<VerifyResetPasswordRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.doResetPassword(any()) } returns mockVerifyResetPasswordResponse
            repository.doResetPassword(mockVerifyResetPasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.doResetPassword(any()) }
            }
        }
    }

    @Test
    fun `doRegisterPassword, with result success`() {
        val mockVerifyResetPasswordResponse = mockk<VerifyResetPasswordResponse>(relaxed = true)
        val mockVerifyResetPasswordRequest = mockk<VerifyResetPasswordRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.doResetPassword(any()) } returns mockVerifyResetPasswordResponse
            repository.doResetPassword(mockVerifyResetPasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { remoteDataSource.doResetPassword(any()) }
            }
        }
    }

    @Test
    fun `doResetPassword, with result Error`() {
        val mockVerifyResetPasswordRequest = mockk<VerifyResetPasswordRequest>(relaxed = true)
        runTest {
            coEvery { remoteDataSource.doResetPassword(any()) } throws IllegalStateException("Mock error")
            repository.doResetPassword(mockVerifyResetPasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.doResetPassword(any()) }
            }
        }
    }

    @Test
    fun isLogin() {
        runTest {
            coEvery { remoteSessionManager.getToken() }
        }
    }

    @Test
    fun clearToken() {
        runTest {
            coEvery { remoteSessionManager.clearData() }
        }
    }
}