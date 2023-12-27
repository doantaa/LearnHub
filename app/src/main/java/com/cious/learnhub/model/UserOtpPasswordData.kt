package com.cious.learnhub.model

import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Email
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserOtpPasswordData(
    val email: String,
    val hashOtp: String,
    val otp: Int
): Parcelable