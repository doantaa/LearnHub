package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthenticationData(
    val name: String,
    val phoneNumber: Long,
    val email: String,
    val password: String,
    val hashOtp: String
) : Parcelable