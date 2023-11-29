package com.cious.data.network.api.model.course


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Module(
    @SerializedName("courseId")
    val courseId: Int?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title:")
    val title: String?,
    @SerializedName("Videos")
    val videos: List<Video?>?
)