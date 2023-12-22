package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.payment.PaymentResponse
import com.cious.learnhub.data.network.api.service.PaymentService

interface PaymentDataSource {
    suspend fun createPayment(id: Int): PaymentResponse
}

class PaymentApiDataSource(
    private val service: PaymentService
) : PaymentDataSource {
    override suspend fun createPayment(id: Int): PaymentResponse {
        return service.createPayment(id)
    }

}