package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Module(
    val courseId: Int,
    val duration: Int,
    val id: Int,
    val title: String,
    val isLocked: Boolean,
    val videos: List<@RawValue Video>
) : Parcelable

