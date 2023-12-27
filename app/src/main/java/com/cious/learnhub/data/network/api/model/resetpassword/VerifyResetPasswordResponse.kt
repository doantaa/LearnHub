package com.cious.learnhub.data.network.api.model.resetpassword


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.cious.learnhub.model.VerifyResetPasswordData

@Keep
data class VerifyResetPasswordResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)

fun VerifyResetPasswordResponse.toVerifyResetPasswordData() = VerifyResetPasswordData(
    message = this.message.orEmpty()
)
