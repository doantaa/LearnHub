package com.cious.learnhub.data.repository

import com.cious.learnhub.data.network.api.datasource.PaymentDataSource
import com.cious.learnhub.data.network.api.model.payment.toPaymentData
import com.cious.learnhub.model.PaymentData
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun createPayment(id: Int) : Flow<ResultWrapper<PaymentData>>
}

class PaymentRepositoryImpl(
    private val dataSource: PaymentDataSource
): PaymentRepository{
    override fun createPayment(id: Int): Flow<ResultWrapper<PaymentData>> {
        return proceedFlow {
            dataSource.createPayment(id).paymentDataResponse.toPaymentData()
        }
    }

}