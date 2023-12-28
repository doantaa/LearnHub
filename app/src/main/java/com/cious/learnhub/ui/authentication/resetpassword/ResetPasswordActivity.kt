package com.cious.learnhub.ui.authentication.resetpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.databinding.ActivityResetPasswordBinding
import com.cious.learnhub.model.UserResetData
import com.cious.learnhub.utils.ApiException
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ResetPasswordActivity : AppCompatActivity() {

    private val binding: ActivityResetPasswordBinding by lazy {
        ActivityResetPasswordBinding.inflate(layoutInflater)
    }
    private val viewModel: ResetPasswordViewModel by viewModel { parametersOf(intent?.extras) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        onFocusForm()
        observeResult()
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSendLink.setOnClickListener {
            sendOtpRequest()
            clearAttributeView()
        }
    }

    private fun sendOtpRequest() {
        val email = binding.etEmail.text.toString().trim()
        if (checkEmailValidation(email)) {
            val otpRequest = OtpRequest(email)
            viewModel.sendOtpPassword(otpRequest)
        }
    }

    private fun clearAttributeView() {
        binding.tilEmail.clearFocus()
        hideKeyboard(binding.tilEmail)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilEmail.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilEmail.setEndIconDrawable(R.drawable.ic_false)
            binding.tilEmail.setEndIconTintList(colorState)

            binding.llMessage.isVisible = true
            binding.llMessage.backgroundTintList = colorState
            binding.tvMessage.text = getString(R.string.text_email_address_is_incorrect_format)
            false
        } else {
            binding.tilEmail.setBackgroundResource(R.drawable.bg_form)
            binding.tilEmail.endIconDrawable = null
            true
        }
    }

    private fun onFocusForm() {
        binding.etEmail.onFocusChangeListener = View.OnFocusChangeListener { _, onFocus ->
            if (onFocus) {
                binding.tilEmail.endIconDrawable = null
            }
        }
    }

    private fun observeResult() {
        viewModel.otpRequestResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnSendLink.isVisible = false
                },
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnSendLink.isVisible = true
                    binding.btnSendLink.isVisible = false

                    val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
                    val message = it.payload?.message
                    binding.llMessage.isVisible = true
                    binding.llMessage.backgroundTintList = colorState
                    binding.tvMessage.text = message

                    processSendOtp(it.payload?.data.toString())
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnSendLink.isVisible = true
                    if (it.exception is ApiException) {
                        val message = it.exception.getParsedError()?.message.orEmpty()
                        binding.llMessage.isVisible = true
                        binding.tvMessage.text = message
                    }
                }
            )
        }
    }

    private fun processSendOtp(otpPayload: String) {
        val userResetData = UserResetData(
            email = binding.etEmail.text.toString(),
            data = otpPayload
        )
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            OtpPasswordActivity.startActivity(this, userResetData)
        }, 2000)
    }
}