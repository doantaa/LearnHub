package com.cious.learnhub.data.network.api.model.course

import com.google.gson.annotations.SerializedName

data class CategoryNameResponse(
    @SerializedName("name")
    val categoryName: String?
)
