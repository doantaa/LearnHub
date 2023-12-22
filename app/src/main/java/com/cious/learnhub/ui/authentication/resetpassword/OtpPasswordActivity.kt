package com.cious.learnhub.ui.authentication.resetpassword

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cious.learnhub.databinding.ActivityOtpBinding

class OtpPasswordActivity : AppCompatActivity() {
    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            navigateToResetPassword()
        }
        binding.btnSubmit.setOnClickListener {

        }
    }

    private fun navigateToResetPassword() {
//        startActivity(Intent(this, ResetPasswordActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//        })
    }
}