package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Enrollment(
    val id: Int,
    val title: String,
    val about: String,
    val objective: String,
    val onboarding: String,
    val categoryId: String,
    val categoryName: String,
    val level: String,
    val courseType: String,
    val imageUrl: String,
    val rating: Double,
    val instructor: String,
    val telegramLink: String,
    val moduleCount: Int,
    val price: Int,
    val totalDuration: String,
    val progress: Double,
    val updatedAt: String,
    val createdAt: String,
    val module: List<@RawValue Module>,
):Parcelable

