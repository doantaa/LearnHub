package com.cious.learnhub.data.network.api.model.register


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class RegisterRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("hashedOtp")
    val hashedOtp: String?,
)