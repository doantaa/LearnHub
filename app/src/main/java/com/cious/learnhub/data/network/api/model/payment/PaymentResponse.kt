package com.cious.learnhub.data.network.api.model.payment


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentResponse(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val paymentDataResponse: PaymentDataResponse
)