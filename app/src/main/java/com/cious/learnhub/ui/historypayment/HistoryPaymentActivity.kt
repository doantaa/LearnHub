package com.cious.learnhub.ui.historypayment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cious.learnhub.data.dummy.DummyHistoryPaymentDataSourceImpl
import com.cious.learnhub.databinding.ActivityHistoryPaymentBinding
import com.cious.learnhub.model.HistoryPayment
import com.cious.learnhub.ui.historypayment.adapter.HistoryPaymentAdapter
import com.cious.learnhub.ui.historypayment.adapter.HistoryPaymentTypeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryPaymentActivity : AppCompatActivity() {

    lateinit var binding: ActivityHistoryPaymentBinding
    private val viewModel: HistoryPaymentViewModel by viewModel()



    private val adapterPayment : HistoryPaymentAdapter by lazy {
        HistoryPaymentAdapter(HistoryPaymentTypeAdapter.PAID){

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        setUpRecyclerView()
        setClickListener()
        Log.d("ambil data",viewModel.getTransaction().toString())

    }



    private fun setData() {
        viewModel.getTransaction()
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpRecyclerView() {
        binding.rvHistoryPayment.adapter = adapterPayment

    }

}
