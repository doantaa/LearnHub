package com.cious.learnhub.model

import com.cious.learnhub.data.network.api.model.profile.CourseItemResponse
import com.cious.learnhub.data.network.api.model.profile.UserTransactionItemResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class UserTransaction(

    val amount: Int?,
    val courseItemResponse: CourseItemResponse?,
    val courseId: Int?,
    val createdAt: String?,
    val date: String?,
    val grossAmount: Int?,
    val id: String?,
    val paymentType: String?,
    val status: String?,
    val transactionDate: String?,
    val updatedAt: String?,
    val userId: Int?
)


