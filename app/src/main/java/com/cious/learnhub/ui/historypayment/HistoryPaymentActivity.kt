package com.cious.learnhub.ui.historypayment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.data.dummy.DummyHistoryPaymentDataSourceImpl
import com.cious.learnhub.databinding.ActivityHistoryPaymentBinding
import com.cious.learnhub.model.HistoryPayment
import com.cious.learnhub.ui.historypayment.adapter.HistoryPaymentAdapter
import com.cious.learnhub.ui.historypayment.adapter.HistoryPaymentTypeAdapter

class HistoryPaymentActivity : AppCompatActivity() {

    lateinit var binding: ActivityHistoryPaymentBinding
    private val adapterPayment : HistoryPaymentAdapter by lazy {
        HistoryPaymentAdapter(HistoryPaymentTypeAdapter.PAID){
            historyPayment : HistoryPayment ->
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpRecyclerView() {
        binding.rvHistoryPayment.adapter = adapterPayment
        adapterPayment.setData(DummyHistoryPaymentDataSourceImpl().getListPayment())
    }

}
