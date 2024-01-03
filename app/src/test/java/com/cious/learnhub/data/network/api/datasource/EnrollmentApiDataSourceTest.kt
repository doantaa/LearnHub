package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.category.CategoriesResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentDetailResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentsResponse
import com.cious.learnhub.data.network.api.model.enrollments.ProgressResponse
import com.cious.learnhub.data.network.api.service.EnrollmentService
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.Enrollment
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class EnrollmentApiDataSourceTest {


    private lateinit var enrollmentDataSource: EnrollmentDataSource

    @MockK
    private lateinit var enrollmentService: EnrollmentService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        enrollmentDataSource = EnrollmentApiDataSource(enrollmentService)
    }

    @Test
    fun getEnrollment() {
        runTest {
            val mockResponse = mockk<EnrollmentsResponse>(relaxed = true)
            coEvery {
                enrollmentService.getEnrollments()
            } returns mockResponse

            val response = enrollmentDataSource.getEnrollment()
            coVerify {
                enrollmentService.getEnrollments()
            }

            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCourseById() {
        runTest {
            val mockResponse = mockk<EnrollmentDetailResponse>(relaxed = true)
            coEvery {
                enrollmentService.getCourseById(any())
            } returns mockResponse

            val response = enrollmentDataSource.getCoursesById(1)
            coVerify {
                enrollmentService.getCourseById(any())
            }

            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun postProgress() {
        runTest {
            val mockResponse = mockk<ProgressResponse>(relaxed = true)
            coEvery {
                enrollmentService.postProgress(any())
            } returns mockResponse

            val response = enrollmentDataSource.postProgress(1)
            coVerify {
                enrollmentService.postProgress(any())
            }

            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCategory() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery {
                enrollmentService.getCategories()
            } returns mockResponse

            val response = enrollmentDataSource.getCategory()
            coVerify {
                enrollmentService.getCategories()
            }
            assertEquals(response, mockResponse)
        }
    }
}