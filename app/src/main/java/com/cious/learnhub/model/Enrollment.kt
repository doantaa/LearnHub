package com.cious.learnhub.model

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
    val progress: Int,
    val updatedAt: String,
    val createdAt: String
)

