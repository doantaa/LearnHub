package com.cious.learnhub.ui.myclass

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cious.learnhub.databinding.FragmentHomeBinding
import androidx.fragment.app.viewModels
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.datasource.EnrollmentApiDataSource
import com.cious.learnhub.data.network.api.service.EnrollmentService
import com.cious.learnhub.data.repository.EnrollmentRepositoryImpl
import com.cious.learnhub.databinding.FragmentMyClassBinding
import com.cious.learnhub.model.Course
import com.cious.learnhub.ui.home.HomeViewModel
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.proceedWhen

class MyClassFragment : Fragment() {

    private lateinit var binding: FragmentMyClassBinding



    private val viewModel: MyClassViewModel by viewModels {
        val service = EnrollmentService.invoke(ChuckerInterceptor(requireContext()))
        val dataSource = EnrollmentApiDataSource(service)
        val repository = EnrollmentRepositoryImpl(dataSource)
        GenericViewModelFactory.create(MyClassViewModel(repository))
    }

    private val progressiveCourseAdapter: ProgressiveCourseAdapter by lazy {
        ProgressiveCourseAdapter {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setupRecyclerView()
        observeData()
    }

    private fun observeData() {
        observeCourseData()
        observeCategoryData()
    }

    private fun observeCourseData() {
        viewModel.courses.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { _ ->
                binding.rvClass.apply {
                    isVisible = true
                    adapter = progressiveCourseAdapter
                }
                it.payload?.let { data ->
                    progressiveCourseAdapter.setData(data)
                }
            })
        }
    }

    private fun observeCategoryData() {
}

private fun getData() {
    viewModel.getCourses()
}

private fun setupRecyclerView() {
    setupCategoryRecyclerView()
    setupCourseRecyclerView()
}

private fun setupCourseRecyclerView() {
    binding.rvClass.adapter = progressiveCourseAdapter
}

private fun setupCategoryRecyclerView() {
}
}