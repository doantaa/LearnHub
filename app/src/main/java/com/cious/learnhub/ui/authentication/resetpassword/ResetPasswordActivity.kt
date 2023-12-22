package com.cious.learnhub.ui.authentication.resetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.databinding.ActivityResetPasswordBinding
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordActivity : AppCompatActivity() {

    private val binding: ActivityResetPasswordBinding by lazy {
        ActivityResetPasswordBinding.inflate(layoutInflater)
    }
    private val viewModel: ResetPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        observeOtpRequestResult()
    }

    private fun observeOtpRequestResult() {
        viewModel.otpRequestResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnSendLink.isVisible = false
                },
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnSendLink.isVisible = true
                    processSendOtp()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnSendLink.isVisible = true
                }
            )
        }
    }

    private fun processSendOtp() {
        navigateToOtp()
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            navigateToLogin()
        }
        binding.btnSendLink.setOnClickListener {
            sendOtpRequest()
        }
    }

    private fun sendOtpRequest() {
        val email = binding.etEmail.text.toString()
        val otpRequest = OtpRequest(email)
        viewModel.sendOtpPassword(otpRequest)
    }

    private fun navigateToOtp() {
        startActivity(Intent(this, OtpPasswordActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        finish()
    }
}