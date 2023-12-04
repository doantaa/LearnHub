package com.cious.learnhub.ui.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityRegisterBinding
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.utils.highLightWord

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
        setupForm()
    }

    private fun setupForm() {
        binding.etPhoneNumber.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
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
            doRegister()
        }
    }

//    private fun isFormValid(): Boolean {
//        val fullName = binding.etName.text.toString().trim()
//        val email = binding.etEmail.text.toString().trim()
//        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
//        val password = binding.etPassword.text.toString().trim()
//
//        return checkNameValidation(fullName)
//    }

//    private fun checkNameValidation(fullName: String): Boolean {
//        return if (fullName.isEmpty()) {
//            binding.
//        }
//    }

    private fun doRegister() {
//        if (isFormValid()) {
//
//        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        finish()
    }
}