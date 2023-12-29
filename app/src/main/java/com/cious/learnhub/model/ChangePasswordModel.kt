package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class ChangePasswordModel(
    val message: String,
) : Parcelable


