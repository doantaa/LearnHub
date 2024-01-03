package com.cious.learnhub.data.repository

import app.cash.turbine.test
import com.cious.learnhub.data.network.api.datasource.CourseApiDataSource
import com.cious.learnhub.data.network.api.model.category.CategoriesResponse
import com.cious.learnhub.data.network.api.model.category.CategoryItemResponse
import com.cious.learnhub.data.network.api.model.course.CourseItemResponse
import com.cious.learnhub.data.network.api.model.course.CoursesResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentDetailResponse
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CourseRepositoryImplTest {

    @MockK
    lateinit var apiDataSource: CourseApiDataSource
    private lateinit var repository: CourseRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CourseRepositoryImpl(apiDataSource)
    }

    @Test
    fun `get courses, result loading`(){
        val mockResponse = mockk<CoursesResponse>()
        runTest {
            coEvery { apiDataSource.getCourses() } returns mockResponse
            repository.getCourses("category","null","null","null").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                println(data)
            }
        }
    }

    @Test
    fun `get courses, result success`(){
        val mockItemResponse = mockk<CourseItemResponse>(relaxed = true)
        val mockResponse = CoursesResponse(data = listOf(mockItemResponse), "String", true)
        runTest {
            coEvery { apiDataSource.getCourses() } returns mockResponse
            repository.getCourses().map {
                delay(100)
                it
            }.test {
                delay(3110)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Success)
                coVerify { apiDataSource.getCourses() }
            }
        }
    }


    @Test
    fun `get courses, result empty`(){
        val mockResponse = CoursesResponse(data = emptyList(), "String", true)
        runTest {
            coEvery { apiDataSource.getCourses() } returns mockResponse
            repository.getCourses().map {
                delay(100)
                it
            }.test {
                delay(3110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { apiDataSource.getCourses() }
            }
        }
    }

    @Test
    fun `get course by id, result loading`(){
        val mockResponse = mockk<EnrollmentDetailResponse>(relaxed = true)
        runTest {
            coEvery { apiDataSource.getCoursesById(any()) } returns mockResponse
            repository.getCoursesById(1).map {
                delay(100)
                it
            }.test {
                delay(101)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                apiDataSource.getCoursesById(1).dataDetailResponse
            }
        }
    }


    @Test
    fun `get course by id, result success`(){
        val mockResponse = mockk<EnrollmentDetailResponse>(relaxed = true)
        runTest {
            coEvery { apiDataSource.getCoursesById(any()) } returns mockResponse
            repository.getCoursesById(1).map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { apiDataSource.getCoursesById(any()) }
            }
        }
    }

    @Test
    fun `get categories, result loading`(){
        val mockCategory = mockk<CategoryItemResponse>()
        val mockResponse = CategoriesResponse(data = listOf(mockCategory), "Success", true)
        coEvery { apiDataSource.getCategory() } returns mockResponse

        runTest {
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(100)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { apiDataSource.getCategory() }
            }
        }
    }


    @Test
    fun `get categories, result success`(){
        val mockCategory = mockk<CategoryItemResponse>(relaxed = true)
        val mockResponse = CategoriesResponse(data = listOf(mockCategory), "Success", true)
        coEvery { apiDataSource.getCategory() } returns mockResponse

        runTest {
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { apiDataSource.getCategory() }
            }
        }
    }

}