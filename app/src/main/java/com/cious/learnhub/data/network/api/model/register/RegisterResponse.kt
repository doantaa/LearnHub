package com.cious.learnhub.data.network.api.model.register


import androidx.annotation.Keep
import com.cious.learnhub.model.RegisterData
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val isSuccess: Boolean?,
    @SerializedName("token")
    val token: String?
)

fun RegisterResponse.toRegisterData() = RegisterData(
    message = this.message.orEmpty(),
    token = this.token.orEmpty()
)