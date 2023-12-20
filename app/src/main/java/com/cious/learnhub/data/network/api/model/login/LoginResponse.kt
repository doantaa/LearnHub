package com.cious.learnhub.data.network.api.model.login

import androidx.annotation.Keep
import com.cious.learnhub.model.LoginData
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse (
    @SerializedName("success")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("token")
    val token: String?
)

fun LoginResponse.toLoginData() = LoginData(
    message = this.message.orEmpty(),
    token = this.token.orEmpty()
)