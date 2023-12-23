package com.cious.learnhub.data.network.api.model.course


import androidx.annotation.Keep
import com.cious.learnhub.model.Video
import com.google.gson.annotations.SerializedName

@Keep
data class VideoResponse(
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isLocked")
    val isLocked: Boolean,
    @SerializedName("moduleId")
    val moduleId: Int?,
    @SerializedName("no")
    val no: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("videoUrl")
    val videoUrl: String?
)

fun VideoResponse.toVideo() = Video (
    duration = this.duration ?: 0,
    id = this.id ?: 0,
    isLocked = this.isLocked,
    moduleId = this.moduleId ?: 0,
    no = this.no ?: 0,
    title = this.title.orEmpty(),
    videoUrl = this.videoUrl.orEmpty()
)

fun Collection<VideoResponse>.toVideoList() = this.map { it.toVideo() }