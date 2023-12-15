package com.cious.learnhub.ui.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.ui.main.MainActivity
import com.cious.learnhub.R
import com.cious.learnhub.data.UserPreferenceDataStore
import com.cious.learnhub.data.network.api.datasource.AuthDataSourceImpl
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.network.api.service.AuthenticationService
import com.cious.learnhub.data.repository.AuthRepositoryImpl
import com.cious.learnhub.databinding.ActivityLoginBinding
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.ui.authentication.resetpassword.ResetPasswordActivity
import com.cious.learnhub.ui.home.HomeFragment
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.highLightWord
import com.cious.learnhub.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels {
        val service = AuthenticationService.invoke(ChuckerInterceptor(this))
        val dataSource = AuthDataSourceImpl(service)
        val repository = AuthRepositoryImpl(dataSource)
        val userPreferenceDataStore: UserPreferenceDataStore by inject()
        GenericViewModelFactory.create(LoginViewModel(repository, userPreferenceDataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        observeResult()
    }

    private fun observeResult() {
        viewModel.loginRequestResult.observe(this) {
            it.proceedWhen (
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    navigateToHome()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isVisible = false
                },
                doOnError = {},
                doOnEmpty = {}
            )
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
        binding.tvIntentResetPassword.highLightWord(getString(R.string.text_lupa_kata_sandi)) {
            navigateToResetPassword()
        }
        binding.tvIntentRegister.highLightWord(getString(R.string.text_highlight_register)) {
            navigateToRegister()
        }
        binding.tvIntentGuestMode.setOnClickListener() {
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
        startActivity(
            Intent(this, ResetPasswordActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

    private fun navigateToHomeGuestMode() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

    private fun navigateToRegister() {
        startActivity(
            Intent(this, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }
}