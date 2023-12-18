package com.cious.learnhub.data.network.response.customer

import com.google.gson.annotations.SerializedName

data class CustomerResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
