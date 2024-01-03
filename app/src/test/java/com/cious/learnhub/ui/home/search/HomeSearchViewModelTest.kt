package com.cious.learnhub.ui.home.search

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.model.Course
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    @MockK
    private lateinit var mockBundle: Bundle

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { mockBundle.getString("EXTRA_TITLE") } returns "title"

        homeSearchViewModel = spyk(HomeSearchViewModel(mockBundle, courseRepository))
    }

    @Test
    fun `get title extra`(){
        val result = homeSearchViewModel.title
        Assert.assertEquals(result, "title")
        coVerify { mockBundle.getString(any()) }
    }

    @Test
    fun `get course search`(){
        val mockCourse1 = mockk<Course>(relaxed = true)
        val mockCourse2 = mockk<Course>(relaxed = true)


        coEvery { courseRepository.getCourses(any<String>(), any(), any(), any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockCourse1,mockCourse2))
                )
        }
        homeSearchViewModel.getCourse()
        val result = homeSearchViewModel.course.getOrAwaitValue()
        Assert.assertEquals(result.payload?.size, 2)
        coVerify { courseRepository.getCourses(any<String>(), any(),any(),any()) }

    }
}