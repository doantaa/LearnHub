package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.payment.PaymentDataResponse
import com.cious.learnhub.data.network.api.model.payment.PaymentResponse
import com.cious.learnhub.data.network.api.service.PaymentService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PaymentApiDataSourceTest {

    @MockK
    private lateinit var service: PaymentService
    lateinit var dataSource: PaymentDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = PaymentApiDataSource(service)
    }


    @Test
    fun `create payment`(){
        val mockDataResponse = mockk<PaymentDataResponse>()
        val fakeResponse = PaymentResponse(true, "Success", mockDataResponse)

        runTest {
            coEvery { service.createPayment(any()) } returns fakeResponse

            val response = dataSource.createPayment(1)
            coVerify { service.createPayment(any()) }
            Assert.assertEquals(response, fakeResponse)
        }
    }
}