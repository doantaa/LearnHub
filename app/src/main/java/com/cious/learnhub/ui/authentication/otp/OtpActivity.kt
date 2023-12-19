package com.cious.learnhub.ui.authentication.otp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.datasource.AuthDataSourceImpl
import com.cious.learnhub.data.network.api.service.AuthenticationService
import com.cious.learnhub.data.repository.AuthRepositoryImpl
import com.cious.learnhub.databinding.ActivityOtpBinding
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.proceedWhen

class OtpActivity : AppCompatActivity() {

    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }
    private val viewModel: OtpViewModel by viewModels{
        val service = AuthenticationService.invoke(ChuckerInterceptor(this), applicationContext)
        val dataSource = AuthDataSourceImpl(service)
        val repository = AuthRepositoryImpl(dataSource)
        GenericViewModelFactory.create(OtpViewModel(repository, intent.extras))
    }

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

                },
                doOnEmpty = {},
                doOnError = {}
            )
        }
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
        val USER_REGISTER_DATA = "USER_REGISTER_DATA"
        fun startActivity(context: Context, data: AuthenticationData) {
            val intent = Intent(context, OtpActivity::class.java)
            intent.putExtra(USER_REGISTER_DATA, data)
            context.startActivity(intent)
        }
    }
}