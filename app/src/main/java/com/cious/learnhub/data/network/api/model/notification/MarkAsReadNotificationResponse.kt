package com.cious.learnhub.data.network.api.model.notification

import androidx.annotation.Keep
import com.cious.learnhub.model.MarkAsReadNotification
import com.google.gson.annotations.SerializedName

@Keep
data class MarkAsReadNotificationResponse(
    @SerializedName("message")
    val message: String?
)

fun MarkAsReadNotificationResponse.toMarkAsReadNotification() = MarkAsReadNotification(
    message=this.message.orEmpty()
)
