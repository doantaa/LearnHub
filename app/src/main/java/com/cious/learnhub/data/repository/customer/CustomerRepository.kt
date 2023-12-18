package com.cious.learnhub.data.repository.customer

import com.cious.learnhub.data.Resource
import com.cious.learnhub.data.network.response.customer.AddCostumerBody
import com.cious.learnhub.data.network.response.customer.CustomerResponse
import kotlinx.coroutines.flow.Flow


interface CustomerRepository {
    fun addCustomer (token:String, addCostumerBody: AddCostumerBody) : Flow<Resource<CustomerResponse>>
}