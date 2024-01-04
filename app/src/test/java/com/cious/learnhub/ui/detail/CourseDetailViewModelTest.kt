package com.cious.learnhub.ui.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.EnrollmentRepository
import com.cious.learnhub.model.Enrollment
import com.cious.learnhub.model.Progress
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.tools.getOrAwaitValue
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CourseDetailViewModelTest {

    @MockK
    lateinit var repository: EnrollmentRepository

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    private lateinit var viewModel: CourseDetailViewModel

    private val mockBundle = mockk<Bundle>(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { repository.getCoursesById(1) } returns flow {
            emit(
                ResultWrapper.Success(
                    Enrollment(
                        1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        1.0,
                        "",
                        "",
                        1,
                        1,
                        "",
                        1.0,
                        "",
                        "",
                        listOf()
                    )
                )
            )

        }

        coEvery { repository.getCoursesById(any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    Enrollment(
                        1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        1.0,
                        "",
                        "",
                        1,
                        1,
                        "",
                        1.0,
                        "",
                        "",
                        listOf()
                    )
                )
            )

        }

        coEvery { repository.postProgress(any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    Progress(
                        "",
                        true
                    )
                )
            )
        }

        viewModel = spyk(
            CourseDetailViewModel(mockBundle, repository),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `get course extra`() {
        coEvery { mockBundle.getInt("EXTRA_ID") } returns 1
        val result = viewModel.courseId
        println(mockBundle.getInt("EXTRA_ID"))
        Assert.assertEquals(result, 0)
    }

    @Test
    fun `get course by id`() {
        viewModel.getCourseById(1)
        val result = viewModel.enrollment.getOrAwaitValue()
        assertTrue(result is ResultWrapper.Success)
        coVerify { repository.getCoursesById(any()) }
    }

    @Test
    fun `refresh course list`() {
        viewModel.refreshCourseList()
        val result = viewModel.updatedVideoList.getOrAwaitValue()
        assertTrue(result is ResultWrapper.Success)
        coVerify { repository.getCoursesById(any()) }
    }

    @Test
    fun `post progress`(){
        viewModel.postProgress(1)
        val result = viewModel.progress.getOrAwaitValue()
        assertTrue(result is ResultWrapper.Success)
        coVerify { repository.postProgress(any()) }
    }
}