package com.cious.learnhub.data.network.api.model.enrollments

import com.cious.learnhub.data.network.api.model.enrollments.Data
import com.google.gson.annotations.SerializedName

data class EnrollmentDetailResponse (
    @SerializedName("data")
    val dataDetailResponse: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)