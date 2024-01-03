package com.cious.learnhub.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.data.repository.EnrollmentRepository
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.ProfileModel
import com.cious.learnhub.tools.MainCoroutineRule
import com.cious.learnhub.tools.getOrAwaitValue
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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

class HomeViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var courseRepository: CourseRepository

    @MockK
    private lateinit var profileRepository: ProfileRepository

    @MockK
    private lateinit var enrollmentRepository: EnrollmentRepository

    lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)


        coEvery { courseRepository.getCourses(any<String>(), popularity = "asc") } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }

        coEvery { enrollmentRepository.getEnrollment(any<String>()) } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(mockk(relaxed = true), mockk(relaxed = true))
                )
            )
        }

        every { courseRepository.getCategories() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(Category("id", "name", "image"), Category("id", "name", "image") )
                )
            )
        }

        coEvery { profileRepository.getProfile() } returns flow {
            emit(
                ResultWrapper.Success(
                    ProfileModel(
                        city = "City",
                        country = "Country",
                        email = "eamil",
                        id = 1,
                        membership = "asdf",
                        name = "berry",
                        phoneNumber = "68399",
                        profileUrl = "asdlfj",
                        role = "asdflas"
                    )
                )
            )
        }

        viewModel = spyk(
            HomeViewModel(
                courseRepository,profileRepository,enrollmentRepository
            )
        )
    }


    @Test
    fun `get course popular live data`(){
        viewModel.getCourses()
        val result = viewModel.courses.getOrAwaitValue()
        Assert.assertEquals(result.payload?.size, 2)
        println(result.payload?.size)
        coVerify { courseRepository.getCourses(popularity = "asc") }
    }


    @Test
    fun `get categories live data`(){
        viewModel.getCategories()
    //        verify { courseRepository.getCategories() }
//        Assert.assertEquals(result.payload?.size, 2)
    }


    @Test
    fun `get enrollment live data`(){
        val result = viewModel.enrollment.getOrAwaitValue()
        Assert.assertEquals(result.payload?.size, 2)
        println(result.payload?.size)
        coVerify { enrollmentRepository.getEnrollment() }
    }

    @Test
    fun `get user live data`(){
        viewModel.getUserProfile()
        val result = viewModel.userData.getOrAwaitValue()
        Assert.assertEquals(result.payload?.name, "berry")
        println(result.payload)
        coVerify { profileRepository.getProfile() }
    }


}