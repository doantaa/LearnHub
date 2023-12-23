package com.cious.learnhub.data.network.api.model.course


import androidx.annotation.Keep
import com.cious.learnhub.model.Module
import com.google.gson.annotations.SerializedName

@Keep
data class ModuleResponse(
    @SerializedName("courseId")
    val courseId: Int?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("isLocked")
    val isLocked: Boolean,
    @SerializedName("Videos")
    val videoResponses: List<VideoResponse>?
)

fun ModuleResponse.toModule() = Module(
    id = this.id ?: 0,
    courseId = this.courseId ?: 0,
    duration = this.duration ?: 0,
    title = this.title.orEmpty(),
    isLocked = this.isLocked,
    videos = this.videoResponses?.toVideoList() ?: emptyList()
)

fun Collection<ModuleResponse>.toModuleList() = this.map { it.toModule() }