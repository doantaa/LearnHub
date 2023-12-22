package com.cious.learnhub.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore


val Context.appDataStore by preferencesDataStore(
    name = "Learn Hub"
)