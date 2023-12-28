package com.cious.learnhub.ui.authentication.resetpassword

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityOtpBinding
import com.cious.learnhub.model.UserOtpPasswordData
import com.cious.learnhub.model.UserResetData
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class OtpPasswordActivity : AppCompatActivity() {

    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }
    private val viewModel: OtpPasswordViewModel by viewModel { parametersOf(intent?.extras) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setupView()
        setClickListeners()
    }

    private val onBackPressedCallback = object  : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showConfirmBackPressDialog()
        }
    }

    private fun showConfirmBackPressDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.text_konfirmasi))
        builder.setMessage(getString(R.string.text_apakah_anda_yakin_ingin_kembali))

        builder.setPositiveButton(getString(R.string.text_ya)) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()

            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        builder.setNegativeButton(getString(R.string.text_tidak)) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder.show()
    }

    private fun setupView() {
        binding.llOtpInfo.isInvisible = true
        val email = viewModel.dataParcel?.email
        val oldSubtitle = getString(R.string.text_ketik_6_digit_kode_yang_dikirimkan_ke_email)
        val newSubtitle = "$oldSubtitle <b>$email</b>"
        binding.tvSubtitle.text = Html.fromHtml(newSubtitle, Html.FROM_HTML_MODE_COMPACT)
    }

    private fun setClickListeners() {
        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSubmit.setOnClickListener {
            binding.pbLoading.isVisible = true
            binding.btnSubmit.isVisible = false
            sendDataParcel()
        }
    }

    private fun sendDataParcel() {
        val otp = binding.otpView.text.toString()
        if (checkOtpValidation(otp)) {
            val userOtpPasswordData = UserOtpPasswordData(
                email = viewModel.dataParcel?.email.toString(),
                hashOtp = viewModel.dataParcel?.data.toString(),
                otp = otp.toInt()
            )
            VerifyResetPasswordActivity.startActivity(this, userOtpPasswordData)
        }
    }

    private fun checkOtpValidation(otp: String): Boolean {
        return if (otp.length < 6) {
            binding.llMessage.isVisible = true
            binding.tvMessage.text =
                getString(R.string.text_please_make_sure_you_ve_entered_the_complete_6_digit_otp_code)
            false
        } else {
            binding.llMessage.isVisible = false
            true
        }
    }

    companion object {
        const val USER_RESET_DATA = "USER_RESET_DATA"
        fun startActivity(context: Context, data: UserResetData) {
            val intent = Intent(context, OtpPasswordActivity::class.java)
            intent.putExtra(USER_RESET_DATA, data)
            context.startActivity(intent)
        }
    }
}