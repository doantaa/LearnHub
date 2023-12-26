package com.cious.learnhub.data.network.api.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UpdatePassword(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)