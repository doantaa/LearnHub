package com.cious.learnhub.ui.myclass

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.data.repository.EnrollmentRepository
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
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MyClassViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var enrollmentRepository: EnrollmentRepository

    @MockK
    private lateinit var authRepository: AuthRepository

    private lateinit var viewModel: MyClassViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { enrollmentRepository.getEnrollment(any<String>()) } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }

        coEvery { enrollmentRepository.getCategories() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }

        coEvery { authRepository.isLogin() } returns true

        viewModel = spyk(
            MyClassViewModel(
                enrollmentRepository, authRepository
            )
        )
    }

    @Test
    fun `get enrollment live data`(){
        viewModel.getCourses()
        val result = viewModel.enrollment.getOrAwaitValue()
        assertEquals(result.payload?.size, 2)
        println(result.payload?.size)
        coVerify { enrollmentRepository.getEnrollment() }
    }

    @Test
    fun `get category live data`(){
        val result = viewModel.categories.getOrAwaitValue()
        assertEquals(result.payload?.size, 2)
        println(result.payload?.size)
        coVerify { enrollmentRepository.getCategories() }
    }

    @Test
    fun `is Login`(){
        val result = viewModel.isLogin
        assertEquals(result, true)
        coVerify { authRepository.isLogin() }
    }

}