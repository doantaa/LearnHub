package com.cious.learnhub.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Progress (
    val message: String,
    val success: Boolean
): Parcelable