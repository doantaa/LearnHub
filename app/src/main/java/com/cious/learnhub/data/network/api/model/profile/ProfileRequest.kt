package com.cious.learnhub.data.network.api.model.profile

import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.gson.annotations.SerializedName

data class ProfileRequest (
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: Long?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("city")
    val city:String?,
)