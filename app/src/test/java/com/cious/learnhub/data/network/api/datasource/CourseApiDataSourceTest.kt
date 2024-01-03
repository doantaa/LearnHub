package com.cious.learnhub.data.network.api.datasource

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.model.category.CategoriesResponse
import com.cious.learnhub.data.network.api.model.course.CoursesResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentDetailResponse
import com.cious.learnhub.data.network.api.service.CourseService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CourseApiDataSourceTest {

    @MockK
    lateinit var service: CourseService
    private lateinit var dataSource: CourseDataSource
    private lateinit var chuckerInterceptor: ChuckerInterceptor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = CourseApiDataSource(service)
        chuckerInterceptor = mockk()
    }

    @Test
    fun `get all course`() {
        runTest {
            val mockResponse = mockk<CoursesResponse>(relaxed = true)
            coEvery { service.getCourses() } returns mockResponse

            val response = dataSource.getCourses()
            coVerify { service.getCourses() }

            assertEquals(response, mockResponse)
        }
    }


    @Test
    fun `get course with category`() {
        runTest {
            val mockResponse = mockk<CoursesResponse>(relaxed = true)
            coEvery { service.getCourses(category = any<String>()) } returns mockResponse

            val response = dataSource.getCourses(category = "category")
            coVerify { service.getCourses(category = any<String>()) }

            assertEquals(response, mockResponse)
        }
    }


    @Test
    fun `get course with title`() {
        runTest {
            val mockResponse = mockk<CoursesResponse>(relaxed = true)
            coEvery { service.getCourses(title = any()) } returns mockResponse

            val response = dataSource.getCourses(title = "title")
            coVerify { service.getCourses(title = any()) }

            assertEquals(response, mockResponse)
        }
    }


    @Test
    fun getCoursesById() {
        runTest {
            val mockResponse = mockk<EnrollmentDetailResponse>(relaxed = true)
            coEvery { service.getCourseById(any()) } returns mockResponse

            val response = dataSource.getCoursesById(1)
            assertEquals(response,mockResponse)

        }
    }

    @Test
    fun getCategory() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse

            val response = dataSource.getCategory()
            coVerify { service.getCategories() }
            assertEquals(response, mockResponse)

        }
    }



}