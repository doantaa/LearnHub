package com.cious.learnhub.data.network.api.model.payment


import androidx.annotation.Keep
import com.cious.learnhub.model.PaymentData
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentDataResponse(
    @SerializedName("token")
    val token: String?,
    @SerializedName("redirect_url")
    val redirectUrl: String?
)


fun PaymentDataResponse.toPaymentData() = PaymentData(
    redirectUrl = this.redirectUrl.orEmpty(),
    token = this.token.orEmpty()
)
