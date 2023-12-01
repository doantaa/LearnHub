package com.cious.learnhub.data.network.api.model.course


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.cious.learnhub.model.Course

@Keep
data class CourseItemResponse(
    @SerializedName("about")
    val about: String?,
    @SerializedName("categoryId")
    val categoryId: String?,
    @SerializedName("courseType")
    val courseType: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("instructor")
    val instructor: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("moduleCount")
    val moduleCount: Int?,
    @SerializedName("Modules")
    val modules: List<Module?>,
    @SerializedName("objective")
    val objective: String?,
    @SerializedName("onboaring")
    val onboaring: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("telegramLink")
    val telegramLink: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("totalDuration")
    val totalDuration: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

fun CourseItemResponse.toCourse() = Course(
    id = this.id ?: 0,
    title = this.title.orEmpty(),
    categoryId = this.categoryId.orEmpty(),
    courseType = this.courseType.orEmpty(),
    price = this.price ?: 0,
    rating = this.rating ?: 0.0,
    instructor = this.instructor.orEmpty(),
    level = this.level.orEmpty(),
    moduleCount = this.moduleCount ?: 0,
    totalDuration = this.totalDuration.orEmpty(),
    imageUrl = this.imageUrl.orEmpty(),
    onboaring = this.onboaring.orEmpty(),
    objective = this.objective.orEmpty(),
    about = this.about.orEmpty(),
    telegramLink = this.telegramLink.orEmpty(),
    createdAt = this.createdAt.orEmpty()
)

fun Collection<CourseItemResponse>.toCourseList() = this.map { it.toCourse() }