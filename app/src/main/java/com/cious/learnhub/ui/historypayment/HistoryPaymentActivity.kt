package com.cious.learnhub.ui.historypayment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cious.learnhub.databinding.ActivityHistoryPaymentBinding
import com.cious.learnhub.ui.historypayment.adapter.HistoryPaymentAdapter
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryPaymentActivity : AppCompatActivity() {

    lateinit var binding: ActivityHistoryPaymentBinding
    private val viewModel: HistoryPaymentViewModel by viewModel()



    private val adapterPayment : HistoryPaymentAdapter by lazy {
        HistoryPaymentAdapter{

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        setUpRecyclerView()
        setClickListener()
        observeData()
        Log.d("ambil data",viewModel.getTransaction().toString())

    }

    private fun observeData() {
        viewModel.transaction.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        adapterPayment.setData(it)
                    }
                }
            )
        }
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
