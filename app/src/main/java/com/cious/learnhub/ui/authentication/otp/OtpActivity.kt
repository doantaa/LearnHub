package com.cious.learnhub.ui.authentication.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cious.learnhub.databinding.ActivityOtpBinding
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.model.RegisterData
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.ui.main.MainActivity
import com.cious.learnhub.utils.SessionManager
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class OtpActivity : AppCompatActivity() {

    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }
    private val viewModel: OtpViewModel by viewModel { parametersOf(intent?.extras) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        observeResult()
    }

    private fun observeResult() {
        viewModel.registerResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen (
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnSubmit.isVisible = false
                },
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnSubmit.isVisible = true
                    saveToken(it.payload)
                },
                doOnEmpty = {},
                doOnError = {}
            )
        }
    }

    private fun saveToken(registerData: RegisterData?) {
        val token = registerData?.token
        Log.d("token register", token.toString())
        if (!token.isNullOrBlank()) {
            token.let { SessionManager.saveAuthToken(this, it) }
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            navigateToLogin()
        }
        binding.btnSubmit.setOnClickListener {
            val dataParcel = viewModel.dataParcel
            val authenticationData = AuthenticationData(
                name = dataParcel?.name.orEmpty(),
                email = dataParcel?.email.orEmpty(),
                phoneNumber = dataParcel?.phoneNumber ?: 0,
                password = dataParcel?.password.orEmpty(),
                hashOtp = dataParcel?.hashOtp.orEmpty()
            )
            val otp = binding.otpView.text.toString()

            viewModel.doRegister(authenticationData, otp)
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, RegisterActivity::class.java)
        )
        finish()
    }

    companion object {
        const val USER_REGISTER_DATA = "USER_REGISTER_DATA"
        fun startActivity(context: Context, data: AuthenticationData) {
            val intent = Intent(context, OtpActivity::class.java)
            intent.putExtra(USER_REGISTER_DATA, data)
            context.startActivity(intent)
        }
    }
}