package com.cious.learnhub.data.network.api.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.cious.learnhub.model.ProfileModel

@Keep
data class ProfileDataResponse(
    @SerializedName("city")
    val city:String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("membership")
    val membership: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("profileUrl")
    val profileUrl: String?,
    @SerializedName("role")
    val role: String?
)

fun ProfileDataResponse.toProfile() =ProfileModel(
    city=this.city.orEmpty(),
    country=this.country.orEmpty(),
    email=this.email.orEmpty(),
    id = this.id ?: 0,
    membership=this.membership.orEmpty(),
    name=this.name.orEmpty(),
    phoneNumber=this.phoneNumber.orEmpty(),
    profileUrl=this.profileUrl.orEmpty(),
    role=this.role.orEmpty(),
)