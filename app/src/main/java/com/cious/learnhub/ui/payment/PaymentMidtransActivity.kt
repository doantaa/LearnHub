package com.cious.learnhub.ui.payment

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.cious.learnhub.databinding.ActivityPaymentMidtransBinding

class PaymentMidtransActivity : AppCompatActivity() {

    private val binding: ActivityPaymentMidtransBinding by lazy {
        ActivityPaymentMidtransBinding.inflate(layoutInflater)
    }

    var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        extractIntentData()
        openUrlFromWebView(url)
        setButtonListener()
    }

    private fun setButtonListener() {
        binding.btnBack.setOnClickListener {
            val deeplink = "learnhubapp://cious.learnhub.com"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplink))
            startActivity(intent)
        }
    }

    private fun extractIntentData() {
        if (intent.hasExtra("URL")) {
            url = intent.getStringExtra("URL")
        }
    }

    private fun openUrlFromWebView(url: String?) {
        if (url != null) {
            val webView: WebView = binding.wvMidtrans
            webView.webViewClient = object : WebViewClient() {
                val pd = ProgressDialog(this@PaymentMidtransActivity)

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val requestUrl = request?.url.toString()
                    if (requestUrl.contains("gojek://") ||
                        requestUrl.contains("shopeeid://") ||
                        requestUrl.contains("//wsa.wallet.airpay.co.id/") ||
                        requestUrl.contains("/gopay/partner/") ||
                        requestUrl.contains("/shopeepay/")
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, request?.url)
                        startActivity(intent)
                        return true
                    } else if (requestUrl.contains("cious.learnhub.com/")) {
                        Log.d("URLADAKAG", request?.url.toString())
                        val deeplink = "learnhubapp://cious.learnhub.com"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplink))
                        startActivity(intent)
                        return true

                    } else {
                        return false
                    }
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    pd.setMessage("loading")
                    pd.show()
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    pd.hide()
                    super.onPageFinished(view, url)
                }
            }

            webView.settings.loadsImagesAutomatically = true
            webView.settings.javaScriptEnabled = true
            webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webView.loadUrl(url)
        }
    }
}