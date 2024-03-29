package com.cious.learnhub.data.network.api.model.course


import androidx.annotation.Keep
import com.cious.learnhub.model.Course
import com.google.gson.annotations.SerializedName

@Keep
data class CourseItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("about")
    val about: String?,
    @SerializedName("objective")
    val objective: String?,
    @SerializedName("onboaring")
    val onboaring: String?,
    @SerializedName("categoryId")
    val categoryId: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("courseType")
    val courseType: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("instructor")
    val instructor: String?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("telegramLink")
    val telegramLink: String?,
    @SerializedName("moduleCount")
    val moduleCount: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("totalDuration")
    val totalDuration: String?,
    @SerializedName("Modules")
    val moduleResponses: List<ModuleResponse>?,
    @SerializedName("Category")
    val category: CategoryNameResponse?
)

fun CourseItemResponse.toCourse() = Course(
    id = this.id ?: 0,
    title = this.title.orEmpty(),
    categoryId = this.categoryId.orEmpty(),
    categoryName = this.category?.categoryName.orEmpty(),
    courseType = this.courseType.orEmpty(),
    price = this.price ?: 0,
    rating = this.rating ?: 0.0,
    instructor = this.instructor.orEmpty(),
    level = this.level.orEmpty(),
    moduleCount = this.moduleCount ?: 0,
    totalDuration = this.totalDuration.orEmpty(),
    imageUrl = this.imageUrl.orEmpty(),
    onboarding = this.onboaring.orEmpty(),
    objective = this.objective.orEmpty(),
    about = this.about.orEmpty(),
    telegramLink = this.telegramLink.orEmpty(),
    module = this.moduleResponses?.toModuleList() ?: emptyList(),
    createdAt = this.createdAt.orEmpty()
)

fun Collection<CourseItemResponse>.toCourseList() = this.map { it.toCourse() }