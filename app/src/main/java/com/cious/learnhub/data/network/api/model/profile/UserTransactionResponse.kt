package com.cious.learnhub.data.network.api.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.cious.learnhub.model.UserTransaction

@Keep
data class UserTransactionResponse(
    @SerializedName("data")
    val data : List<UserTransactionItemResponse>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)

data class UserTransactionItemResponse(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("Course")
    val courseItemResponse: CourseItemResponse?,
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
data class CourseItemResponse(
    @SerializedName("about")
    val about: String?,
    @SerializedName("Category")
    val category: Category?,
    @SerializedName("categoryId")
    val categoryId: String?,
    @SerializedName("courseType")
    val courseType: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("instructor")
    val instructor: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("moduleCount")
    val moduleCount: Int?,
    @SerializedName("objective")
    val objective: String?,
    @SerializedName("onboarding")
    val onboarding: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("telegramLink")
    val telegramLink: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
fun UserTransactionItemResponse.toTransaction()= UserTransaction(
    amount=this.amount,
    courseItemResponse=this.courseItemResponse,
    courseId=this.courseId,
    createdAt=this.createdAt,
    date=this.date,
    grossAmount=this.grossAmount,
    id=this.id,
    paymentType=this.paymentType,
    status=this.status,
    transactionDate=this.transactionDate,
    updatedAt=this.updatedAt,
    userId=this.userId
)
fun Collection<UserTransactionItemResponse>.toTransactionList()=this.map {
    it.toTransaction()
}