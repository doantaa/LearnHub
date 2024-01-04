package com.cious.learnhub.ui.course

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.model.Category
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

class CourseViewModelTest {

    @MockK
    private lateinit var repository: CourseRepository

    lateinit var viewModel: CourseViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { repository.getCourses() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }

        coEvery { repository.getCategories() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }


        viewModel = spyk(
            CourseViewModel(repository),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `get courses live data`(){
        viewModel.getCourses()
        val result = viewModel.courses.getOrAwaitValue()
        Assert.assertEquals(result.payload?.size, 2)
        println(result.payload?.size)
        coVerify { repository.getCourses() }
    }

    @Test
    fun `get courses live data with string category`(){
        coEvery { repository.getCourses("Category") } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }
        viewModel.getCourses()
        val result = viewModel.courses.getOrAwaitValue()
        Assert.assertEquals(result.payload?.size, 2)
        println(result.payload?.size)
        coVerify { repository.getCourses() }
    }


    @Test
    fun `get categories live data`(){
        val result = viewModel.categories.getOrAwaitValue()
        Assert.assertEquals(result.payload?.size, 2)
        println(result.payload?.size)
        coVerify { repository.getCategories() }
    }

    @Test
    fun `add selected category`(){
        val mockCategory = mockk<Category>(relaxed = true)
        viewModel.addSelectedCategory(mockCategory)

        val result = viewModel.selectedCategories.value
        println(result)

        viewModel.removeSelectedCategory(mockCategory)

    }

}