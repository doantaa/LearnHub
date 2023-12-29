package com.cious.learnhub.data.network.api.model.enrollments


import androidx.annotation.Keep
import com.cious.learnhub.model.Progress
import com.google.gson.annotations.SerializedName


@Keep
data class ProgressResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

fun ProgressResponse.toProgress() = Progress(
    message = this.message,
    success = this.success
)