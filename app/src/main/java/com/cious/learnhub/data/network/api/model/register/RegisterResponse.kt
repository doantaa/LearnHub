package com.cious.learnhub.data.network.api.model.register


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class RegisterResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val isSuccess: Boolean?
)