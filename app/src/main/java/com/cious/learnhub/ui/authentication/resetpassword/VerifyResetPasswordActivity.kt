package com.cious.learnhub.ui.authentication.resetpassword

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordRequest
import com.cious.learnhub.databinding.ActivityVerifyPasswordBinding
import com.cious.learnhub.model.UserOtpPasswordData
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.utils.ApiException
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class VerifyResetPasswordActivity : AppCompatActivity() {

    private val binding: ActivityVerifyPasswordBinding by lazy {
        ActivityVerifyPasswordBinding.inflate(layoutInflater)
    }
    private val viewModel: VerifyResetPasswordViewModel by viewModel { parametersOf(intent?.extras) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        observeResult()
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            processResetPassword()
        }
    }

    private fun processResetPassword() {
        if (isFormValid()) {
            val dataParcel = viewModel.dataParcel
            val verifyResetPasswordRequest = VerifyResetPasswordRequest(
                email = dataParcel?.email.orEmpty(),
                hashedOtp = dataParcel?.hashOtp.orEmpty(),
                otp = dataParcel?.otp ?: 0,
                password = binding.etPassword.text.toString()
            )
            viewModel.doResetPassword(verifyResetPasswordRequest)
        }
    }

    private fun isFormValid(): Boolean {
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        return checkPasswordValidation(password) &&
                checkConfirmPasswordValidation(password, confirmPassword)
    }

    private fun checkPasswordValidation(password: String): Boolean {
        return if (password.length < 8 || password.length > 12) {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilPassword.setEndIconTintList(colorState)
            binding.tilPassword.setBackgroundResource(R.drawable.bg_form_error)
            binding.llMessage.isVisible = true
            binding.tvMessage.text =
                getString(R.string.text_password_minimum_8_characters_and_maximum_12_characters)
            false
        } else {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilPassword.setEndIconTintList(colorState)
            binding.tilPassword.setBackgroundResource(R.drawable.bg_form)
            binding.llMessage.isVisible = false
            true
        }
    }

    private fun checkConfirmPasswordValidation(password: String, confirmPassword: String): Boolean {
        return if (password != confirmPassword) {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilConfirmPassword.setEndIconTintList(colorState)
            binding.tilConfirmPassword.setBackgroundResource(R.drawable.bg_form_error)
            binding.llMessage.isVisible = true
            binding.tvMessage.text =
                getString(R.string.text_password_and_confirm_password_do_not_match)
            false
        } else {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilConfirmPassword.setEndIconTintList(colorState)
            binding.tilConfirmPassword.setBackgroundResource(R.drawable.bg_form)
            binding.llMessage.isVisible = false
            true
        }
    }

    private fun observeResult() {
        viewModel.resetPasswordResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnSave.isVisible = false
                },
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnSave.isVisible = true

                    val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
                    binding.llMessage.backgroundTintList = colorState
                    binding.llMessage.isVisible = true
                    binding.tvMessage.text = it.payload?.message.toString()
                    navigateToLogin()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnSave.isVisible = true
                    binding.llMessage.isVisible = true
                    if (it.exception is ApiException) {
                        val message = it.exception.getParsedError()?.message.orEmpty()
                        binding.tvMessage.text = message
                    }
                }
            )
        }
    }

    private fun navigateToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }, 2000)
    }

    companion object {
        const val USER_OTP_PASSWORD_DATA = "USER_OTP_PASSWORD_DATA"
        fun startActivity(context: Context, data: UserOtpPasswordData) {
            val intent = Intent(context, VerifyResetPasswordActivity::class.java)
            intent.putExtra(USER_OTP_PASSWORD_DATA, data)
            context.startActivity(intent)
        }
    }
}