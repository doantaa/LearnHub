package com.cious.learnhub.data.repository

import android.content.Context
import app.cash.turbine.test
import com.cious.learnhub.data.network.api.datasource.CourseApiDataSource
import com.cious.learnhub.data.network.api.datasource.EnrollmentApiDataSource
import com.cious.learnhub.data.network.api.model.enrollments.Data
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentDetailResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentsResponse
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

class EnrollmentRepositoryImplTest {

    @MockK
    lateinit var enrollmentApiDataSource: EnrollmentApiDataSource

    private lateinit var repository: EnrollmentRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = EnrollmentRepositoryImpl(enrollmentApiDataSource)
    }

    @Test
    fun `get enrollment, result loading`() {
        val mockkResponse = mockk<EnrollmentsResponse>()
        runTest {
            coEvery { enrollmentApiDataSource.getEnrollment() } returns mockkResponse
            repository.getEnrollment("category", "null", "null", "null").map {
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
    fun `get enrollment, result success`() {
        val mockItemResponse = mockk<Data>(relaxed = true)
        val mockResponse = EnrollmentsResponse(true, "String", data = listOf(mockItemResponse))
        runTest {
            coEvery { enrollmentApiDataSource.getEnrollment() } returns mockResponse
            repository.getEnrollment().map {
                delay(100)
                it
            }.test {
                delay(3110)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Success)
                coVerify { enrollmentApiDataSource.getEnrollment() }
            }
        }
    }

    @Test
    fun `get enrollment by id, loading`() {
        val mockResponse = mockk<EnrollmentDetailResponse>(relaxed = true)
        runTest {
            coEvery {
                enrollmentApiDataSource.getCoursesById(any())
            } returns mockResponse
            repository.getCoursesById(1).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Loading)
                println(data)
            }
        }
    }

    @Test
    fun `get enrollment by id, success`() {
        val mockItemResponse = mockk<Data>(relaxed = true)
        val mockResponse = EnrollmentDetailResponse(mockItemResponse, "String", true)

        runTest {
            coEvery { enrollmentApiDataSource.getCoursesById(any()) } returns mockResponse
            repository.getCoursesById(1).map {
                delay(100)
                it
            }.test {
                delay(3110)
                val data = expectMostRecentItem()
                println("ini data $data")
                assertTrue(data is ResultWrapper.Success)
                coVerify { enrollmentApiDataSource.getCoursesById(any()) }
            }
        }
    }
}