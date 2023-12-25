package com.cious.learnhub.ui.authentication.resetpassword

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.cious.learnhub.model.UserResetData

class OtpPasswordViewModel(
    private val extras: Bundle?
): ViewModel() {

    val dataParcel = extras?.getParcelable<UserResetData>(OtpPasswordActivity.USER_RESET_DATA)
}