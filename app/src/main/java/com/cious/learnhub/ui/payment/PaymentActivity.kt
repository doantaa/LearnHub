package com.cious.learnhub.ui.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cious.learnhub.databinding.ActivityPaymentBinding
import com.cious.learnhub.databinding.BottomSheetPaymentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class PaymentActivity : AppCompatActivity() {
    private val binding: ActivityPaymentBinding by lazy {
        ActivityPaymentBinding.inflate(layoutInflater)
    }
    private val bottomSheet: BottomSheetPaymentBinding by lazy {
        BottomSheetPaymentBinding.inflate(layoutInflater)
    }
    private lateinit var dialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun showDialogPayment() {
        dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheet.root)
        dialog.show()
    }


    private fun setOnClickListener() {
        binding.btnContinue.setOnClickListener {
            showDialogPayment()
        }

        bottomSheet.btnContinueToDetail.setOnClickListener {
            val intent = Intent(this, PaymentDetailActivity::class.java)
            startActivity(intent)
        }
    }


}