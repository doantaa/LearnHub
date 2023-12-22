package com.cious.learnhub.data.network.api.model.course

import com.google.gson.annotations.SerializedName

data class CategoryNameResponse(
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("name")
    val categoryName: String?
)
