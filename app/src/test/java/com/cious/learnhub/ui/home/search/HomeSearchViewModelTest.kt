package com.cious.learnhub.ui.home.search

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

class HomeSearchViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var courseRepository: CourseRepository

    lateinit var homeSearchViewModel: HomeSearchViewModel

    @Before
    fun setUp() {
        coEvery { courseRepository.getCourses(any<String>(), popularity = "asc") } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }

        val mockExtras = mockk<Bundle>()
        homeSearchViewModel = spyk(HomeSearchViewModel(mockExtras, courseRepository))
    }
}