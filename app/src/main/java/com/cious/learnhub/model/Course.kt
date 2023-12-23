package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Course(
    val id: Int,
    val title: String,
    val categoryId: String,
    val categoryName: String,
    val courseType: String,
    val price: Int,
    val rating: Double,
    val instructor: String,
    val level: String,
    val moduleCount: Int,
    val totalDuration: String,
    val imageUrl: String,
    val onboarding: String,
    val objective: String,
    val about: String,
    val telegramLink: String,
    val createdAt: String,
    val module: List<@RawValue Module>
):Parcelable
