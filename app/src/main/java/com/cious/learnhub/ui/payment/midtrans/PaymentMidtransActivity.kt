package com.cious.learnhub.ui.payment.midtrans

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.cious.learnhub.databinding.ActivityPaymentMidtransBinding
import com.cious.learnhub.ui.detail.CourseDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentMidtransActivity : AppCompatActivity() {

    private val binding: ActivityPaymentMidtransBinding by lazy {
        ActivityPaymentMidtransBinding.inflate(layoutInflater)
    }

    private val viewModel: PaymentMidtransViewModel by viewModel {
        parametersOf(intent?.extras)
    }

    var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getMidtransUrl()
        openUrlFromWebView(url)
    }

    private fun getMidtransUrl() {
        if (intent.hasExtra(URL)) {
            url = viewModel.url
        }
    }

    private fun openUrlFromWebView(url:  String?) {
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

                        val deeplink = "learnhubapp://cious.learnhub.com"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplink))
                        startActivity(intent)
                        val id = viewModel.courseId ?: 0
                        CourseDetailActivity.startActivity(this@PaymentMidtransActivity, id)
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

    companion object {
        const val URL = "URL"
        const val ID = "ID"
        fun navigateToPaymentMidtrans(context: Context, url: String, id: Int){
            val intent = Intent(context, PaymentMidtransActivity::class.java)
            intent.putExtra(URL, url)
            intent.putExtra(ID, id)
            context.startActivity(intent)
        }
    }
}