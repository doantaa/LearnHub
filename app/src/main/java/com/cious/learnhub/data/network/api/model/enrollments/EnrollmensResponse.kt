package com.cious.learnhub.data.network.api.model.enrollments

import com.google.gson.annotations.SerializedName

data class EnrollmentsResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<Data>? = null
)