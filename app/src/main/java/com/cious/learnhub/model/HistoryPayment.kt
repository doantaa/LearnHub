package com.cious.learnhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class HistoryPayment (
    val id: Int? = null,
    val name: String,
    val imgUrl: String,
    val author: String,
    val rating: Double,
    val level: String,
    val modul: String,
    val duration: String
): Parcelable