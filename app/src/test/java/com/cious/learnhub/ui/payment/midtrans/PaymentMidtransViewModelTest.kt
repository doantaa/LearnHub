package com.cious.learnhub.ui.payment.midtrans

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cious.learnhub.data.repository.PaymentRepository
import com.cious.learnhub.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PaymentMidtransViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var mockBundle: Bundle

    @MockK
    private lateinit var repository: PaymentRepository

    lateinit var viewModel: PaymentMidtransViewModel
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { mockBundle.getString("URL") } returns "ini adalah url"
        coEvery { mockBundle.getInt("ID") } returns 2

        viewModel = spyk(
            PaymentMidtransViewModel(repository, mockBundle)
        )
    }

    @Test
    fun `get extra url`(){
        val result = viewModel.url
        Assert.assertEquals(result, "ini adalah url")
        coVerify { mockBundle.getString(any()) }
    }

    @Test
    fun `get extra id`(){
        val result = viewModel.courseId
        Assert.assertEquals(result, 2)
        coVerify { mockBundle.getInt(any()) }
    }
}