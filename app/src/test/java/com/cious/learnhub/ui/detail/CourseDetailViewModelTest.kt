package com.cious.learnhub.ui.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bitohur.foodapp.tools.MainCoroutineRule
import com.cious.learnhub.data.network.api.model.course.CourseDetailResponse
import com.cious.learnhub.data.repository.EnrollmentRepository
import com.cious.learnhub.model.Enrollment
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
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CourseDetailViewModelTest {

    @MockK
    lateinit var repository: EnrollmentRepository

    @MockK
    lateinit var bundle: Bundle

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    private lateinit var viewModel: CourseDetailViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            CourseDetailViewModel(bundle, repository),
            recordPrivateCalls = true
        )
    }
}