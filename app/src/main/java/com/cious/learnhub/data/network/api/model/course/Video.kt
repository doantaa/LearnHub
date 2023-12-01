package com.cious.learnhub.data.network.api.model.course


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Video(
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("moduleId")
    val moduleId: Int?,
    @SerializedName("no")
    val no: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("videoUrl")
    val videoUrl: String?
)