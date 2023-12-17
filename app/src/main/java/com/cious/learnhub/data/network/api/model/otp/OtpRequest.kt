package com.cious.learnhub.data.network.api.model.otp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class OtpRequest (
    @SerializedName("email")
    val email: String
)