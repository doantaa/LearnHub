package com.cious.learnhub.ui.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cious.learnhub.MainActivity
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityLoginBinding
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.ui.authentication.resetpassword.ResetPasswordActivity
import com.cious.learnhub.utils.highLightWord

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
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