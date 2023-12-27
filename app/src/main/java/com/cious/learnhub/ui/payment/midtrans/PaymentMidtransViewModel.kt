package com.cious.learnhub.ui.payment.midtrans

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.cious.learnhub.data.repository.PaymentRepository

class PaymentMidtransViewModel(
    private val repository: PaymentRepository, extras: Bundle?
): ViewModel() {
    val url = extras?.getString("URL")
    val courseId = extras?.getInt("ID")
}