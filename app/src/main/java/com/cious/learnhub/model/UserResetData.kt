package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResetData(
    val email: String,
    val data: String
) : Parcelable