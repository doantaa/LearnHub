package com.cious.learnhub.data.network.api.model.course

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryNameResponse(
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("name")
    val categoryName: String
)
