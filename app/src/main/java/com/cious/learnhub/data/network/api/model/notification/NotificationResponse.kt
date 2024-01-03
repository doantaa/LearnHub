package com.cious.learnhub.data.network.api.model.notification


import androidx.annotation.Keep
import com.cious.learnhub.model.NotificationData
import com.google.gson.annotations.SerializedName

@Keep
data class NotificationResponse(
    @SerializedName("data")
    val data: Unit,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)

fun NotificationResponse.toNotificationData() = NotificationData(
    message = this.message.orEmpty()
)