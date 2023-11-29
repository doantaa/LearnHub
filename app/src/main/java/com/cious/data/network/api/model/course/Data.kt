package com.cious.data.network.api.model.course


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("courses")
    val courses: List<Course?>?
)