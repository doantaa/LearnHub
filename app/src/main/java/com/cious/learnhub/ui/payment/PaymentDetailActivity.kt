package com.cious.learnhub.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.cious.learnhub.databinding.ActivityPaymentDetailBinding
import com.cious.learnhub.ui.authentication.login.LoginActivity

class PaymentDetailActivity : AppCompatActivity() {

    private val binding: ActivityPaymentDetailBinding by lazy {
        ActivityPaymentDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnBuyNow.setOnClickListener {
            navigateToPaymentMidtrans()
        }
    }

    private fun navigateToPaymentMidtrans() {
        val intent = Intent(this, PaymentMidtransActivity::class.java)
        intent.putExtra("URL", getUrl())
        startActivity(intent)
    }

    private fun getUrl(): String {
        return "https://sample-demo-dot-midtrans-support-tools.et.r.appspot.com/snap-redirect/"
    }
}