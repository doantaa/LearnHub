package com.cious.learnhub.data.network.api.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ProfileResponse(
    @SerializedName("data")
    val data: ProfileDataResponse,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)