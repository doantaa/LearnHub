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

        setupWebview()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {

    }

    private fun setupWebview() {
        val webView = binding.wvMidtrans
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://app.sandbox.midtrans.com/snap/v3/redirection/d3f0c6b7-6593-4983-9dbc-15e0e0427c4f#/payment-list")
    }
}