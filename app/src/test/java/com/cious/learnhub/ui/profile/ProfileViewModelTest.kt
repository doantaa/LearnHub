package com.cious.learnhub.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var repository: AuthRepository
    lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { repository.isLogin() } returns true
        coEvery { repository.clearToken() } returns Unit
        viewModel = spyk(
            ProfileViewModel(repository)
        )
    }

    @Test
    fun `check user login`(){
        val result = viewModel.isLogin
        Assert.assertEquals(result, true)
        coVerify { repository.isLogin() }
    }

    @Test
    fun `clear token`(){
        viewModel.clearToken()
        coVerify { repository.clearToken() }
    }


}