package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationModel(
    val id: Int,
    val category: String,
    val datetime: String,
    val title: String,
    val description: String,
) : Parcelable