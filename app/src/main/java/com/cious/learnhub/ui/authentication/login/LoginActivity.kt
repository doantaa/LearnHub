package com.cious.learnhub.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.databinding.ActivityLoginBinding
import com.cious.learnhub.model.LoginData
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.ui.authentication.resetpassword.ResetPasswordActivity
import com.cious.learnhub.ui.main.MainActivity
import com.cious.learnhub.utils.SessionManager
import com.cious.learnhub.utils.highLightWord
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
            navigateToHome()
        }

        setClickListeners()
        observeResult()
    }

    private fun observeResult() {
        viewModel.loginRequestResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen (
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isVisible = false
                },
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    processLogin(it.payload)
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    binding.llMessage.isVisible = true

                    // MASIH Error, handling error:
                    val errorMessage = it.payload?.token.toString()
                    binding.tvMessage.text = errorMessage
                },
                doOnEmpty = {}
            )
        }
    }

    private fun processLogin(loginData: LoginData?) {
        val token = loginData?.token
        Log.d("token", token.toString())
        if (!token.isNullOrBlank()) {
            token.let { SessionManager.saveAuthToken(this, it) }
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun setClickListeners() {
        binding.tvIntentResetPassword.highLightWord(getString(R.string.text_lupa_kata_sandi)) {
            navigateToResetPassword()
        }
        binding.tvIntentRegister.highLightWord(getString(R.string.text_highlight_register)) {
            navigateToRegister()
        }
        binding.tvIntentGuestMode.setOnClickListener {
            navigateToHomeGuestMode()
        }
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val loginRequest = LoginRequest(email, password)
        viewModel.doLoginRequest(loginRequest)
    }

    private fun navigateToResetPassword() {
        startActivity(Intent(this, ResetPasswordActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToHomeGuestMode() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}