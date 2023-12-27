package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class OtpData(
    val message: String,
    val data: String
) : Parcelable