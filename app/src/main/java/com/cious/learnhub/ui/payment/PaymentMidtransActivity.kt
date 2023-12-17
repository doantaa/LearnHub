package com.cious.learnhub.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityPaymentMidtransBinding

class PaymentMidtransActivity : AppCompatActivity() {

    private val binding: ActivityPaymentMidtransBinding by lazy {
        ActivityPaymentMidtransBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}