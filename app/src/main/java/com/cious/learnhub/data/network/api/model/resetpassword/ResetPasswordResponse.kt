package com.cious.learnhub.data.network.api.model.resetpassword


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ResetPasswordResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)
