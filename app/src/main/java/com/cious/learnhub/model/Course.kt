package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
    val onboaring: String,
    val objective: String,
    val about: String,
    val telegramLink: String,
    val createdAt: String
):Parcelable
