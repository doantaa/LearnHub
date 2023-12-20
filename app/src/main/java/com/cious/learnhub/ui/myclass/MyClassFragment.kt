package com.cious.learnhub.ui.myclass

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.R
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
            it.proceedWhen(doOnSuccess = {
                binding.shimmerMyClass.isVisible = false
                binding.rvClass.apply {
                    isVisible = true
                    adapter = progressiveCourseAdapter
                }
                it.payload?.let { data ->
                    progressiveCourseAdapter.setData(data)
                }
                binding.rvClass.clearAnimation()
            }, doOnLoading = {
                binding.rvClass.isVisible = false
                binding.layoutState.root.isVisible = false
                binding.shimmerMyClass.isVisible = true
            },  doOnEmpty = {
                binding.layoutState.root.isVisible = true
                binding.shimmerMyClass.isVisible = false
                binding.rvClass.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.shimmerMyClass.isVisible = false
                binding.rvClass.isVisible = false
                binding.layoutState.tvEmptyTitle.text = getString(R.string.text_error)
                binding.layoutState.tvEmptyDescription.text = it.exception?.message
                Log.d("Exception message", it.exception?.message.orEmpty())
            }
            )
        }
    }

    private fun observeCategoryData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { data ->
                binding.rvCategoryClass.isVisible = true
                binding.shimmerMyClassCategories.isVisible = false
                data.payload?.let { categoryList ->
                    categoryListAdapter.setData(categoryList)
                }
                binding.rvCategoryClass.clearAnimation()
            }, doOnLoading = {
                binding.rvCategoryClass.isVisible = false
                binding.shimmerMyClassCategories.isVisible = true

            }, doOnEmpty = {
                binding.rvCategoryClass.isVisible = false
                binding.shimmerMyClassCategories.isVisible = false


            }, doOnError = {
                binding.rvCategoryClass.isVisible = false
                binding.shimmerMyClassCategories.isVisible = false
                Log.d("CATEGORY", it.message.toString())
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