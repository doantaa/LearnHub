package com.cious.learnhub.ui.payment.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.PaymentRepository
import com.cious.learnhub.model.Enrollment
import com.cious.learnhub.model.PaymentData
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

class PaymentViewModelTest {

    @MockK
    private lateinit var repository: PaymentRepository

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var mockBundle: Bundle

    lateinit var viewModel: PaymentViewModel

    @Before
    fun setUp() {

        MockKAnnotations.init(this)
        coEvery { mockBundle.getBundle("EXTRA_COURSE") } returns mockBundle
        val bundleCourse = mockBundle.getBundle("EXTRA_COURSE")
        val mockCourse = mockk<Enrollment>()
        coEvery { bundleCourse?.getParcelable<Enrollment>("COURSE") } returns mockCourse

        viewModel = spyk(PaymentViewModel(repository, mockBundle))


    }

    @Test
    fun `get extra bundle`(){
        val result = viewModel.extraCourse
        coVerify { mockBundle.getBundle(any()) }
        Assert.assertTrue(result is Enrollment)
    }

    @Test
    fun `get payment live data`(){
        val mockPaymentData = mockk<PaymentData>(relaxed = true)
        coEvery { repository.createPayment(any()) } returns flow {
            emit(
                ResultWrapper.Success (
                    mockPaymentData
                )
            )
        }

        viewModel.createPayment(1)
        val result = viewModel.paymentData.getOrAwaitValue()
        println(result.payload)
        Assert.assertTrue(result.payload is PaymentData)
        coVerify { repository.createPayment(any()) }

    }
}