package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val duration: Int,
    val id: Int,
    val isLocked: Boolean,
    val moduleId: Int,
    val no: Int,
    val title: String,
    val videoUrl: String
): Parcelable