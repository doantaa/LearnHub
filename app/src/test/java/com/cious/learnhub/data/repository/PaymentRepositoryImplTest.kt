package com.cious.learnhub.data.repository

import app.cash.turbine.test
import com.cious.learnhub.data.network.api.datasource.PaymentDataSource
import com.cious.learnhub.data.network.api.model.payment.PaymentDataResponse
import com.cious.learnhub.data.network.api.model.payment.PaymentResponse
import com.cious.learnhub.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PaymentRepositoryImplTest {

    @MockK
    private lateinit var dataSource: PaymentDataSource
    lateinit var repository: PaymentRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PaymentRepositoryImpl(dataSource)
    }

    @Test
    fun `createPayment, result success with message`() = runTest {
        // Arrange
        val id = 1
        val fakePaymentDataResponse = PaymentDataResponse(token = "token", redirectUrl = "redirect url")
        val fakePaymentResponse = PaymentResponse(
            success = true,
            message = "message",
            paymentDataResponse = fakePaymentDataResponse
        )

        coEvery { dataSource.createPayment(any()) } returns fakePaymentResponse

        repository.createPayment(id).map {
            delay(100)
            it
        }.test {
            delay(101)
            val data = expectMostRecentItem()
            Assert.assertTrue(data is ResultWrapper.Loading)
            coVerify { dataSource.createPayment(any()) }
        }

    }
}