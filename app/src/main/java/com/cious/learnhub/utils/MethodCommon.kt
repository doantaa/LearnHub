package com.cious.learnhub.utils

import android.content.Context
import android.content.Intent
import com.cious.learnhub.ui.authentication.login.LoginActivity

class MethodCommon {

    companion object {
        fun navigateToLogin(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}