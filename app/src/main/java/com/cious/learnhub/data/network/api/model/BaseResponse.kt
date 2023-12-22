package com.cious.learnhub.data.network.api.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val isSuccess: Boolean?
)
