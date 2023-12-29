package com.cious.learnhub.data.network.api.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.cious.learnhub.model.ChangePasswordModel
import com.cious.learnhub.model.UserEditModel

@Keep
data class ChangePasswordResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)
fun ChangePasswordResponse.toChangePasswordModel()= ChangePasswordModel(
    message=this.message.orEmpty()
)