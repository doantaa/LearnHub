package com.cious.learnhub.data.network.api.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.cious.learnhub.model.Category

@Keep
data class CategoryItemResponse(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

fun CategoryItemResponse.toCategory() = Category (
    name = this.name.orEmpty()
)

fun Collection<CategoryItemResponse>.toCategoryList() = this.map { it.toCategory() }

