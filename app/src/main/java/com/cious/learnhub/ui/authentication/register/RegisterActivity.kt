package com.cious.learnhub.ui.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityRegisterBinding
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.ui.authentication.otp.OtpActivity
import com.cious.learnhub.utils.highLightWord
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        setupForm()
        observeOtpRequestResult()
    }

    private fun observeOtpRequestResult() {
        viewModel.otpRequestResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnRegister.isVisible = false
                },
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    processRegister(it.payload.toString())
                },
                doOnError = {

                },
                doOnEmpty = {}
            )
        }
    }

    private fun processRegister(otpPayload: String) {
        val userRegisterData = AuthenticationData(
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            phoneNumber = fullPhoneNumber().toLong(),
            password = binding.etPassword.text.toString(),
            hashOtp = otpPayload,
        )

        OtpActivity.startActivity(this, userRegisterData)
    }

    private fun fullPhoneNumber(): String {
        val countryCode = "62"
        val userPhoneNumber = binding.etPhoneNumber.text.toString()
        return countryCode + userPhoneNumber
    }

    private fun setupForm() {
        binding.etPhoneNumber.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (v as TextView).hint = null
            } else {
                (v as TextView).hint = getString(R.string.text_plus62)
            }
        }
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            navigateToLogin()
        }
        binding.tvIntentRegister.highLightWord(getString(R.string.text_highlight_login)) {
            navigateToLogin()
        }
        binding.btnRegister.setOnClickListener {
            sendOtpRequest()
        }
    }

    private fun sendOtpRequest() {
        val email = binding.etEmail.text.toString()
        viewModel.sendOtpRequest(email)
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        finish()
    }
}