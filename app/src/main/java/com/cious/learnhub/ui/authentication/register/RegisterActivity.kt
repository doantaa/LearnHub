package com.cious.learnhub.ui.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.highLightWord
import com.cious.learnhub.utils.proceedWhen
import com.cious.learnhub.utils.removeLeadingZeroPhoneNumber
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

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.tvIntentRegister.highLightWord(getString(R.string.text_highlight_login)) {
            navigateToLogin()
        }
        binding.btnRegister.setOnClickListener {
            clearAttributeView()
            sendOtpRequest()
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        finish()
    }

    private fun clearAttributeView() {
        binding.tilName.clearFocus()
        binding.tilEmail.clearFocus()
        binding.tilPhoneNumber.clearFocus()
        binding.tilPassword.clearFocus()

        hideKeyboard(binding.etName)
        hideKeyboard(binding.etEmail)
        hideKeyboard(binding.etPhoneNumber)
        hideKeyboard(binding.etPassword)

        binding.tvIntentRegister.isVisible = false
    }

    private fun sendOtpRequest() {
        if (isFormValid()) {
            binding.llMessage.isVisible = false

            val email = binding.etEmail.text.toString()
            val otpRequest = OtpRequest(email)
            viewModel.sendOtpRegister(otpRequest)
        }
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

    private fun checkNameValidation(name: String): Boolean {
        return if (name.isEmpty()) {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilName.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilName.setEndIconDrawable(R.drawable.ic_false)
            binding.tilName.setEndIconTintList(colorState)
            binding.tvIntentRegister.isVisible = false

            binding.llMessage.isVisible = true
            binding.llMessage.backgroundTintList = colorState
            binding.tvMessage.text = getString(R.string.text_name_cannot_be_empty)
            false
        } else if (name.length > 50) {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilName.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilName.setEndIconDrawable(R.drawable.ic_false)
            binding.tilName.setEndIconTintList(colorState)
            binding.tvIntentRegister.isVisible = false

            binding.llMessage.isVisible = true
            binding.llMessage.backgroundTintList = colorState
            binding.tvMessage.text =
                getString(R.string.text_name_cannot_be_longer_than_50_characters)
            false
        } else {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilName.setBackgroundResource(R.drawable.bg_form)
            binding.tilName.setEndIconDrawable(R.drawable.ic_true)
            binding.tilName.setEndIconTintList(colorState)
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilEmail.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilEmail.setEndIconDrawable(R.drawable.ic_false)
            binding.tilEmail.setEndIconTintList(colorState)
            binding.tvIntentRegister.isVisible = false

            binding.llMessage.isVisible = true
            binding.llMessage.backgroundTintList = colorState
            binding.tvMessage.text = getString(R.string.text_email_address_is_incorrect_format)
            false
        } else {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilEmail.setBackgroundResource(R.drawable.bg_form)
            binding.tilEmail.setEndIconDrawable(R.drawable.ic_true)
            binding.tilEmail.setEndIconTintList(colorState)
            true
        }
    }

    private fun checkPhoneNumberValidation(phoneNumber: String): Boolean {
        val modifiedPhoneNumber = removeLeadingZeroPhoneNumber(phoneNumber)
        return if (modifiedPhoneNumber.isEmpty()) {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilPhoneNumber.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilPhoneNumber.setEndIconDrawable(R.drawable.ic_false)
            binding.tilPhoneNumber.setEndIconTintList(colorState)
            binding.tvIntentRegister.isVisible = false

            binding.llMessage.isVisible = true
            binding.llMessage.backgroundTintList = colorState
            binding.tvMessage.text = getString(R.string.text_phone_number_cannot_be_empty)
            false
        } else if (modifiedPhoneNumber.length <= 9) {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilPhoneNumber.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilPhoneNumber.setEndIconDrawable(R.drawable.ic_false)
            binding.tilPhoneNumber.setEndIconTintList(colorState)
            binding.tvIntentRegister.isVisible = false

            binding.llMessage.isVisible = true
            binding.llMessage.backgroundTintList = colorState
            binding.tvMessage.text = getString(R.string.text_invalid_phone_number_length)
            false
        } else {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilPhoneNumber.setBackgroundResource(R.drawable.bg_form)
            binding.tilPhoneNumber.setEndIconDrawable(R.drawable.ic_true)
            binding.tilPhoneNumber.setEndIconTintList(colorState)
            true
        }
    }

    private fun checkPasswordValidation(password: String): Boolean {
        return if (password.length < 8 || password.length > 12) {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTRED)
            binding.tilPassword.setBackgroundResource(R.drawable.bg_form_error)
            binding.tilPassword.setEndIconTintList(colorState)
            binding.tvIntentRegister.isVisible = false

            binding.llMessage.isVisible = true
            binding.llMessage.backgroundTintList = colorState
            binding.tvMessage.text =
                getString(R.string.text_password_minimum_8_characters_and_maximum_12_characters)
            false
        } else {
            val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
            binding.tilPassword.setBackgroundResource(R.drawable.bg_form)
            binding.tilPassword.setEndIconTintList(colorState)
            true
        }
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
        binding.etPassword.onFocusChangeListener = View.OnFocusChangeListener { _, onFocus ->
            if (onFocus) {
                val colorState = ContextCompat.getColorStateList(this, R.color.NEUTRAL03)
                binding.tilPassword.setEndIconTintList(colorState)
            }
        }
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

                    val colorState = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
                    binding.llMessage.backgroundTintList = colorState
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
            name = binding.etName.text.toString().trim(),
            email = binding.etEmail.text.toString().trim(),
            phoneNumber = fullPhoneNumber().toLong(),
            password = binding.etPassword.text.toString(),
            hashOtp = otpPayload,
        )
        Handler(Looper.getMainLooper()).postDelayed({
            OtpActivity.startActivity(this, userRegisterData)
        }, 2000)
    }

    private fun fullPhoneNumber(): String {
        val countryCode = "62"
        val userPhoneNumber = binding.etPhoneNumber.text.toString()
        val modifiedPhoneNumber = removeLeadingZeroPhoneNumber(userPhoneNumber)
        return countryCode + modifiedPhoneNumber
    }
}