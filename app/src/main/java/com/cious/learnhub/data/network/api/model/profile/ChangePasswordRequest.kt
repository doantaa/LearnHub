package com.cious.learnhub.data.network.api.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ChangePasswordRequest(
    @SerializedName("oldPassword")
    val oldPassword: String?,
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("repeatNewPassword")
    val repeatNewPassword: String?
)