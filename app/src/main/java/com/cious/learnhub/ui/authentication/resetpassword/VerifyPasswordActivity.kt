package com.cious.learnhub.ui.authentication.resetpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityVerifyPasswordBinding

class VerifyPasswordActivity : AppCompatActivity() {

    private val binding: ActivityVerifyPasswordBinding by lazy {
        ActivityVerifyPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnSave.setOnClickListener {

        }
    }
}