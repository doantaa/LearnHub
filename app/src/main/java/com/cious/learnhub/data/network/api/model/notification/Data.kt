package com.cious.learnhub.data.network.api.model.notification


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.cious.learnhub.model.NotificationModel

@Keep
data class NotificationItemResponse(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isRead")
    val isRead: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

fun NotificationItemResponse.toNotificaiton() = NotificationModel(
    id = this.id ?: 0,
    category = "notification",
    datetime = this.updatedAt.orEmpty(),
    title = this.title.orEmpty(),
    description = this.description.orEmpty(),
)

fun Collection<NotificationItemResponse>.toNotificationList() = this.map { it.toNotificaiton() }