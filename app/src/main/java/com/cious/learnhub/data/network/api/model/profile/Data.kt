package com.cious.learnhub.data.network.api.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("Course")
    val course: Course?,
    @SerializedName("courseId")
    val courseId: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("gross_amount")
    val grossAmount: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("payment_type")
    val paymentType: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("transaction_date")
    val transactionDate: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: Int?
)