package com.cious.learnhub.data.network.api.model.resetpassword


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class VerifyResetPasswordRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("hashedOtp")
    val hashedOtp: String?,
    @SerializedName("otp")
    val otp: Int?,
    @SerializedName("password")
    val password: String?
)