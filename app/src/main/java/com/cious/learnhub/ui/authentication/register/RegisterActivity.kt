package com.cious.learnhub.ui.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.databinding.ActivityRegisterBinding
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.ui.authentication.otp.OtpActivity
import com.cious.learnhub.utils.ApiException
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
        onFocusForm()
        observeResult()
    }

    private fun observeResult() {
        viewModel.otpRequestResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnRegister.isVisible = false
                },
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true

                    val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
                    binding.llMessage.backgroundTintList = endIconTint
                    binding.llMessage.isVisible = true
                    binding.tvMessage.text = it.payload?.message.toString()
                    processRegister(it.payload?.data.toString())
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    binding.llMessage.isVisible = true
                    if (it.exception is ApiException) {
                        val message = it.exception.getParsedError()?.message.orEmpty()
                        binding.tvMessage.text = message
                    }
                },
                doOnEmpty = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                }
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

    private fun onFocusForm() {
        binding.etName.onFocusChangeListener = View.OnFocusChangeListener { _, onFocus ->
            if (onFocus) {
                binding.tilName.endIconDrawable = null
            }
        }
        binding.etEmail.onFocusChangeListener = View.OnFocusChangeListener { _, onFocus ->
            if (onFocus) {
                binding.tilEmail.endIconDrawable = null
            }
        }
        binding.etPhoneNumber.onFocusChangeListener = View.OnFocusChangeListener { v, onFocus ->
            if (onFocus) {
                binding.tilPhoneNumber.endIconDrawable = null
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
            clearAttributeForm()
        }
    }

    private fun clearAttributeForm() {
        binding.tilName.clearFocus()
        binding.tilEmail.clearFocus()
        binding.tilPhoneNumber.clearFocus()
        binding.tilPassword.clearFocus()
    }

    private fun isFormValid(): Boolean {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString().trim()
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val password = binding.etPassword.text.toString()

        return checkNameValidation(name) &&
                checkEmailValidation(email) &&
                checkPhoneNumberValidation(phoneNumber) &&
                checkPasswordValidation(password)
    }

    private fun checkPasswordValidation(password: String): Boolean {
        return if (password.length < 8 || password.length > 12) {
            binding.tilPassword.setBackgroundResource(R.drawable.bg_form_error)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilPassword.setEndIconTintList(endIconTint)
            binding.tvIntentRegister.isVisible = false
            binding.llMessage.isVisible = true
            binding.tvMessage.text =
                getString(R.string.text_password_minimum_8_characters_and_maximum_12_characters)
            false
        } else {
            binding.tilPassword.setBackgroundResource(R.drawable.bg_form)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilPassword.setEndIconTintList(endIconTint)
            true
        }
    }

    private fun checkPhoneNumberValidation(phoneNumber: String): Boolean {
        val modifiedPhoneNumber = removeLeadingZeroPhoneNumber(phoneNumber).toString()
        return if (modifiedPhoneNumber.isEmpty()) {
            binding.tilPhoneNumber.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilPhoneNumber.setEndIconDrawable(R.drawable.ic_false)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilPhoneNumber.setEndIconTintList(endIconTint)
            binding.tvIntentRegister.isVisible = false
            binding.llMessage.isVisible = true
            binding.tvMessage.text = getString(R.string.text_phone_number_cannot_be_empty)
            false
        } else if (modifiedPhoneNumber.length <= 9 || phoneNumber.length >= 14) {
            binding.tilPhoneNumber.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilPhoneNumber.setEndIconDrawable(R.drawable.ic_false)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilPhoneNumber.setEndIconTintList(endIconTint)
            binding.tvIntentRegister.isVisible = false
            binding.llMessage.isVisible = true
            binding.tvMessage.text = getString(R.string.text_invalid_phone_number_length)
            false
        } else {
            binding.tilPhoneNumber.setBackgroundResource(R.drawable.bg_form)
            binding.tilPhoneNumber.setEndIconDrawable(R.drawable.ic_true)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilPhoneNumber.setEndIconTintList(endIconTint)
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilEmail.setEndIconDrawable(R.drawable.ic_false)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilEmail.setEndIconTintList(endIconTint)
            binding.tvIntentRegister.isVisible = false
            binding.llMessage.isVisible = true
            binding.tvMessage.text = getString(R.string.text_email_address_is_incorrect_format)
            false
        } else {
            binding.tilEmail.setBackgroundResource(R.drawable.bg_form)
            binding.tilEmail.setEndIconDrawable(R.drawable.ic_true)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilEmail.setEndIconTintList(endIconTint)
            true
        }
    }

    private fun checkNameValidation(name: String): Boolean {
        return if (name.isEmpty()) {
            binding.tilName.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilName.setEndIconDrawable(R.drawable.ic_false)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilName.setEndIconTintList(endIconTint)
            binding.tvIntentRegister.isVisible = false
            binding.llMessage.isVisible = true
            binding.tvMessage.text = getString(R.string.text_name_cannot_be_empty)
            false
        } else if (name.length > 50) {
            binding.tilName.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilName.setEndIconDrawable(R.drawable.ic_false)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilName.setEndIconTintList(endIconTint)
            binding.tvIntentRegister.isVisible = false
            binding.llMessage.isVisible = true
            binding.tvMessage.text =
                getString(R.string.text_name_cannot_be_longer_than_50_characters)
            false
        } else {
            binding.tilName.setBackgroundResource(R.drawable.bg_form)
            binding.tilName.setEndIconDrawable(R.drawable.ic_true)
            val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilName.setEndIconTintList(endIconTint)
            true
        }
    }

    private fun removeLeadingZeroPhoneNumber(phoneNumber: String) {
        if (phoneNumber.startsWith("0")) {
            phoneNumber.substring(1)
        }
    }

    private fun sendOtpRequest() {
        if (isFormValid()) {
            val email = binding.etEmail.text.toString()
            val otpRequest = OtpRequest(email)
            viewModel.sendOtpRegister(otpRequest)
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        finish()
    }
}