package com.cious.learnhub.utils

import android.content.Context
import android.content.SharedPreferences
import com.cious.learnhub.R

class SessionManager(private val prefs: SharedPreferences) {

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        saveString(USER_TOKEN, token)
    }

    private fun saveString(key: String, value: String) {
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getToken(): String? {
        return getString(USER_TOKEN)
    }

    private fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    fun clearData(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}