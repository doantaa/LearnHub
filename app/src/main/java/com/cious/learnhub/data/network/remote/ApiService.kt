package com.cious.learnhub.data.network.remote

import com.cious.learnhub.data.network.response.customer.AddCostumerBody
import com.cious.learnhub.data.network.response.customer.CustomerResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService1{

    @POST("/customer")
    suspend fun addCustomer(
        @Header("Authorization") token: String,
        @Body addCustomerBody: AddCostumerBody
    ) : CustomerResponse
}