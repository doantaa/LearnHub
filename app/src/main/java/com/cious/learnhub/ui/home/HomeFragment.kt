package com.cious.learnhub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.datasource.CourseApiDataSouce
import com.cious.learnhub.data.network.api.service.CourseService
import com.cious.learnhub.data.repository.CourseRepositoryImpl
import com.cious.learnhub.databinding.FragmentHomeBinding
import com.cious.learnhub.ui.home.adapter.CategoryListAdapter
import com.cious.learnhub.ui.home.adapter.CourseListAdapter
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val service = CourseService.invoke(ChuckerInterceptor(requireContext()))
        val dataSource = CourseApiDataSouce(service)
        val repository = CourseRepositoryImpl(dataSource)
        GenericViewModelFactory.create(HomeViewModel(repository))
    }

    private val categoryListAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
        }
    }

    private val courseListAdapter: CourseListAdapter by lazy {
        CourseListAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        invokeData()
        observeData()
    }

    private fun observeData() {
        observeCategoryData()
        observeCourseData()
    }

    private fun observeCourseData() {
        viewModel.courses.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { data ->
                    binding.rvCourseList.isVisible = true
                    data.payload?.let {
                        courseListAdapter.setData(it)
                    }


                },
                doOnLoading = {

                },
                doOnEmpty = {

                },
                doOnError = {

                }
            )
        }
    }

    private fun observeCategoryData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { data ->
                binding.rvCourseCategory.isVisible = true
                data.payload?.let { categoryList ->
                    categoryListAdapter.setData(categoryList)
                }
                Log.d("CATEGORY", data.payload.toString())

            }, doOnLoading = {

            }, doOnEmpty = {

            }, doOnError = {

            })
        }
    }

    private fun invokeData() {
        viewModel.getCourses()
    }

    private fun setupRecyclerView() {
        setupCategoryRecyclerView()
        setupCourseRecyclerView()
    }

    private fun setupCourseRecyclerView() {
        binding.rvCourseList.adapter = courseListAdapter
    }

    private fun setupCategoryRecyclerView() {
        binding.rvCourseCategory.adapter = categoryListAdapter
    }


}