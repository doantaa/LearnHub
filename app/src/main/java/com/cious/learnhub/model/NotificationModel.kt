package com.cious.learnhub.model

data class NotificationModel(
    val id: Int,
    val category: String,
    val datetime: String,
    val title: String,
    val description: String,
    val isRead: Boolean,
)