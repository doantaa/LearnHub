package com.cious.learnhub.ui.authentication.resetpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityOtpBinding
import com.cious.learnhub.model.UserOtpPasswordData
import com.cious.learnhub.model.UserResetData
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class OtpPasswordActivity : AppCompatActivity() {

    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }
    private val viewModel: OtpPasswordViewModel by viewModel { parametersOf(intent?.extras) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setClickListeners()
    }

    private fun setupView() {
        binding.llOtpInfo.isInvisible = true
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSubmit.setOnClickListener {
            binding.pbLoading.isVisible = true
            binding.btnSubmit.isVisible = false
            sendDataParcel()
        }
    }

    private fun sendDataParcel() {
        val otp = binding.otpView.text.toString()
        if (checkOtpValidation(otp)) {
            val userOtpPasswordData = UserOtpPasswordData(
                email = viewModel.dataParcel?.email.toString(),
                hashOtp = viewModel.dataParcel?.data.toString(),
                otp = otp.toInt()
            )
            VerifyResetPasswordActivity.startActivity(this, userOtpPasswordData)
        }
    }

    private fun checkOtpValidation(otp: String): Boolean {
        return if (otp.length < 6) {
            binding.llMessage.isVisible = true
            binding.tvMessage.text =
                getString(R.string.text_please_make_sure_you_ve_entered_the_complete_6_digit_otp_code)
            false
        } else {
            binding.llMessage.isVisible = false
            true
        }
    }

    companion object {
        const val USER_RESET_DATA = "USER_RESET_DATA"
        fun startActivity(context: Context, data: UserResetData) {
            val intent = Intent(context, OtpPasswordActivity::class.java)
            intent.putExtra(USER_RESET_DATA, data)
            context.startActivity(intent)
        }
    }
}