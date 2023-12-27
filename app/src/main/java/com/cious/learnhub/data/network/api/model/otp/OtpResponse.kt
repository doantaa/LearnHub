package com.cious.learnhub.data.network.api.model.otp

import androidx.annotation.Keep
import com.cious.learnhub.model.OtpData
import com.google.gson.annotations.SerializedName

@Keep
data class OtpResponse (
    @SerializedName("success")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: String?
)

fun OtpResponse.toOtpData() = OtpData(
    message = this.message.orEmpty(),
    data = this.data.orEmpty()
)