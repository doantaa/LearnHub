package com.cious.learnhub.data.network.api.model.notification


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NotificationResponse(
    @SerializedName("data")
    val data: List<NotificationItemResponse>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)