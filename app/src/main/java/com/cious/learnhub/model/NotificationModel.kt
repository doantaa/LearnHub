package com.cious.learnhub.model

import android.graphics.drawable.Drawable

data class NotificationModel(
    val id: Int,
    val category: String,
    val datetime: String,
    val title: String,
    val description: String,
)