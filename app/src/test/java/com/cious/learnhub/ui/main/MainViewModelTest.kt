package com.cious.learnhub.ui.main

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

class MainViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())


    @MockK
    private lateinit var repository: AuthRepository
    lateinit var viewModel: MainViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { repository.isLogin() } returns false
        viewModel = spyk(
            MainViewModel(repository)
        )
    }

    @Test
    fun `check user login`(){
        val result = viewModel.isLogin
        Assert.assertEquals(result, false)
        coVerify { repository.isLogin() }
    }
}