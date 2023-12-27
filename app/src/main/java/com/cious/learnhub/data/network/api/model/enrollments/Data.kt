package com.cious.learnhub.data.network.api.model.enrollments

import com.cious.learnhub.data.network.api.model.course.CategoryNameResponse
import com.cious.learnhub.model.Enrollment
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("about")
    val about: String,

    @SerializedName("objective")
    val objective: String,

    @SerializedName("onboaring")
    val onboarding: String,

    @SerializedName("categoryId")
    val categoryId: String,

    @SerializedName("level")
    val level: String,

    @SerializedName("courseType")
    val courseType: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("instructor")
    val instructor: String,

    @SerializedName("telegramLink")
    val telegramLink: String,

    @SerializedName("moduleCount")
    val moduleCount: Int,

    @SerializedName("price")
    val price: Int,

    @SerializedName("totalDuration")
    val totalDuration: String,

    @SerializedName("progress")
    val progress: Double,

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("Category")
    val category: CategoryNameResponse
)

fun Data.toEnrollment() = Enrollment(
    id = this.id,
    title = this.title.orEmpty(),
    about = this.about.orEmpty(),
    objective = this.objective.orEmpty(),
    onboarding = this.onboarding.orEmpty(),
    categoryId = this.categoryId.orEmpty(),
    categoryName = this.category.categoryName.orEmpty(),
    level = this.level.orEmpty(),
    courseType = this.courseType.orEmpty(),
    imageUrl = this.imageUrl.orEmpty(),
    rating = this.rating,
    instructor = this.instructor.orEmpty(),
    telegramLink = this.telegramLink.orEmpty(),
    moduleCount = this.moduleCount,
    price = this.price,
    totalDuration = this.totalDuration.orEmpty(),
    progress = this.progress,
    updatedAt = this.updatedAt.orEmpty(),
    createdAt = this.createdAt.orEmpty()
)

fun Collection<Data>.toEnrollmentList() = this.map { it.toEnrollment() }
