package com.cious.learnhub.data.network.api.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("categories")
    val categories: List<CategoryItemResponse>?
)