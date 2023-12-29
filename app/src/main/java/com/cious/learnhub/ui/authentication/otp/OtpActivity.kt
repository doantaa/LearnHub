package com.cious.learnhub.ui.authentication.otp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.databinding.ActivityOtpBinding
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.model.RegisterData
import com.cious.learnhub.ui.authentication.register.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.cious.learnhub.ui.main.MainActivity
import com.cious.learnhub.utils.ApiException
import com.cious.learnhub.utils.SessionManager
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.highLightWord
import com.cious.learnhub.utils.proceedWhen
import org.koin.core.parameter.parametersOf

class OtpActivity : AppCompatActivity() {

    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }
    private val viewModel: OtpViewModel by viewModel { parametersOf(intent?.extras) }
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        observeResult()
        countDown()
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSubmit.setOnClickListener {
            hideKeyboard(binding.otpView)
            doRegisterRequest()
        }
    }

    private fun doRegisterRequest() {
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

    private fun observeResult() {
        viewModel.registerResult.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnSubmit.isVisible = false
                },
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnSubmit.isVisible = true

                    val endIconTint = ContextCompat.getColorStateList(this, R.color.ALLERTGREEN)
                    binding.llMessage.backgroundTintList = endIconTint
                    binding.llMessage.isVisible = true
                    binding.tvMessage.text = it.payload?.message.toString()
                    saveToken(it.payload)
                    navigateToHome()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnSubmit.isVisible = true
                    binding.llMessage.isVisible = true
                    if (it.exception is ApiException) {
                        val message = it.exception.getParsedError()?.message.orEmpty()
                        binding.tvMessage.text = message
                    }
                }
            )
        }
    }

    private fun saveToken(registerData: RegisterData?) {
        val token = registerData?.token
        if (viewModel.isLogin) {
            token?.let { viewModel.saveAuthToken(it) }
        }
    }

    private fun navigateToHome() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }, 3000)
    }

    private fun countDown() {
        val countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                val secondRemaining = milisUntilFinished / 1000
                val formattedSeconds = secondRemaining.toString() + getString(R.string.text_detik)
                binding.tvCountdown.text = formattedSeconds
            }

            override fun onFinish() {
                binding.llCountdown.isVisible = false
                binding.tvResendOtp.text = getString(R.string.text_kirim_ulang_otp)
                handleResendOtpClick()
            }
        }
        countDownTimer.start()
    }

    private fun handleResendOtpClick() {
        binding.tvResendOtp.highLightWord(getString(R.string.text_kirim_ulang_otp)) {
            val email = viewModel.dataParcel?.email.orEmpty()
            val otpRequest = OtpRequest(email)
            registerViewModel.sendOtpRegister(otpRequest)

            binding.llCountdown.isVisible = true
            countDown()
        }
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