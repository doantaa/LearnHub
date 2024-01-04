package com.cious.learnhub.ui.authentication.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.model.LoginData
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.tools.getOrAwaitValue
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginViewModelTest {

    @MockK
    private lateinit var repository: AuthRepository

    @get: Rule
    val testRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule: TestRule = MainCoroutineRule()

    private lateinit var viewModel: LoginViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val mockLoginData = mockk<LoginData>(relaxed = true)
        coEvery { repository.doLogin(any()) } returns flow {
            emit(ResultWrapper.Success(mockLoginData))
        }

        viewModel = spyk(LoginViewModel(repository))
    }

    @Test
    fun doLoginRequest() {
        val mockRequest = mockk<LoginRequest>(relaxed = true)
        viewModel.doLoginRequest(mockRequest)
        coVerify { repository.doLogin(any()) }
    }
}