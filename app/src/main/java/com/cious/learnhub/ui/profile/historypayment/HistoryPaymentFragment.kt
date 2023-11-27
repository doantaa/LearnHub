package com.cious.learnhub.ui.profile.historypayment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cious.adapter.HistoryPaymentAdapter
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentHistoryPaymentBinding
import com.cious.learnhub.utils.DataDummy

class HistoryPaymentFragment : Fragment() {

    lateinit var binding : FragmentHistoryPaymentBinding

    private lateinit var viewModel: HistoryPaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryPaymentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_historyPaymentFragment_to_navigation_profile)
        }
        fetchListData()
        initRecyclerView()
    }

    private fun fetchListData() {
        binding.rvHistoryPayment.adapter = HistoryPaymentAdapter(DataDummy.listData)
    }

    private fun initRecyclerView() {
        binding.rvHistoryPayment.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHistoryPayment.setHasFixedSize(true)
    }

}