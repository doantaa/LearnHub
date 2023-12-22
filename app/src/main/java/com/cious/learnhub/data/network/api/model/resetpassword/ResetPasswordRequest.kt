package com.cious.learnhub.data.network.api.model.resetpassword


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ResetPasswordRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("hashedOtp")
    val hashedOtp: String?
)