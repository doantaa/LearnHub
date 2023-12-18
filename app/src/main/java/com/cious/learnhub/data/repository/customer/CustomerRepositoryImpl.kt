package com.cious.learnhub.data.repository.customer

import android.util.Log
import com.cious.learnhub.data.Resource
import com.cious.learnhub.data.network.remote.ApiService1
import com.cious.learnhub.data.network.response.customer.AddCostumerBody
import com.cious.learnhub.data.network.response.customer.CustomerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val apiService1: ApiService1
) : CustomerRepository{
    override fun addCustomer(
        token: String,
        addCostumerBody: AddCostumerBody
    ): Flow<Resource<CustomerResponse>> = flow {
        try {
            val response = apiService1.addCustomer(token = "Bearer $token", addCustomerBody = addCostumerBody)
            emit(Resource.Success(response))
        } catch (e : Exception){
            Log.d("TAG", e.message.toString())
        }
    }
}