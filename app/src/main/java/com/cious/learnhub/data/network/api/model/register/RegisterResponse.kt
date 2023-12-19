package com.cious.learnhub.data.network.api.model.register


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.cious.learnhub.model.RegisterData

@Keep
data class RegisterResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val isSuccess: Boolean?
)

fun RegisterResponse.toRegisterData() = RegisterData(
    message = this.message.orEmpty(),
    token = this.message.orEmpty()
)