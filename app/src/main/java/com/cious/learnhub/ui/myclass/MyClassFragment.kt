package com.cious.learnhub.ui.myclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.datasource.EnrollmentApiDataSource
import com.cious.learnhub.data.network.api.service.EnrollmentService
import com.cious.learnhub.data.repository.EnrollmentRepositoryImpl
import com.cious.learnhub.databinding.FragmentMyClassBinding
import com.cious.learnhub.ui.myclass.adapter.CategoryMyClassAdapter
import com.cious.learnhub.ui.myclass.adapter.ProgressiveCourseAdapter
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyClassFragment : Fragment() {

    private lateinit var binding: FragmentMyClassBinding


    private val viewModel: MyClassViewModel by viewModel()

    private val progressiveCourseAdapter: ProgressiveCourseAdapter by lazy {
        ProgressiveCourseAdapter {
        }
    }

    private val categoryListAdapter: CategoryMyClassAdapter by lazy {
        CategoryMyClassAdapter {
            viewModel.getCourses(category = it.id)
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
            it.proceedWhen(doOnSuccess = { data ->

                binding.rvClass.apply {
                    isVisible = true
                    adapter = progressiveCourseAdapter
                }
                data.payload?.let { data ->
                    Log.d("Data Enroll", data.toString())
                    progressiveCourseAdapter.setData(data)
                }
            })
        }
    }

    private fun observeCategoryData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { data ->
                binding.rvCategoryClass.isVisible = true
                data.payload?.let { categoryList ->
                    categoryListAdapter.setData(categoryList)
                }
            })
        }
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
        binding.rvCategoryClass.adapter = categoryListAdapter
    }
}