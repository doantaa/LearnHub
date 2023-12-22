package com.cious.learnhub.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityMainBinding
import com.cious.learnhub.databinding.SheetAuthenticationRequeredBinding
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.utils.SessionManager
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sheetBinding: SheetAuthenticationRequeredBinding
    private lateinit var dialog: BottomSheetDialog
    private var isLoggedIn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isLoggedIn = checkTokenUser()
        setupBottomNav()
    }

    private fun checkTokenUser() : Boolean {
        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
            return true
        }
        return false
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }

    private fun bottomSheetAuthRequered() {
        sheetBinding = SheetAuthenticationRequeredBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(this, R.style.BottomSheetTheme)
        dialog.setContentView(sheetBinding.root)
        dialog.show()

        sheetBinding.btnLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        })
    }
}