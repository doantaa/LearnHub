package com.cious.learnhub.data

import android.content.Context
import android.preference.PreferenceDataStore
import java.util.concurrent.Flow

private const val USER_PREFERENCES_NAME = "user_preferences"

interface UserPreferenceDataStore {
    fun saveToken(token: String)
    fun getToken(): String?
}

class UserPreferenceDataStoreImpl(private val context: Context) : UserPreferenceDataStore {

    private val preferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString("TOKEN_KEY", token)
        editor.apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("TOKEN_KEY", null)
    }

    private val sharedPreferences =
        context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)

}